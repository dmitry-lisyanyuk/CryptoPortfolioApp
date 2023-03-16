package com.batro.util.extension

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.context.GlobalContext
import kotlin.coroutines.resume


fun interval(period: Long, initialDelay: Long = 0) = flow {
    delay(initialDelay)
    while (true) {
        emit(Unit)
        delay(period)
    }
}

suspend fun waitForNetwork() {
    val connectivityManager = GlobalContext
        .get()
        .get<Context>()
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Check if network is already available
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
        return // Network is already available, return immediately
    }

    suspendCancellableCoroutine { continuation ->
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // Network is now available, resume the coroutine
                continuation.resume(Unit)
                connectivityManager.unregisterNetworkCallback(this)
            }
        }

        // Register the network callback and wait for the network to become available
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        // Remove the network callback when the coroutine is cancelled
        continuation.invokeOnCancellation {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
}