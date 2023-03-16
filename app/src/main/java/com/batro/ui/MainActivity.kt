package com.batro.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.batro.ui.screen.addLpTransaction.AddLpTransactionRoute
import com.batro.ui.screen.addCoinTransaction.AddTransactionRoute
import com.batro.ui.screen.delailsPorfolio.coin.DetailsCoinRoute
import com.batro.ui.screen.delailsPorfolio.lp.DetailsLpRoute
import com.batro.ui.screen.editTransaction.coin.EditCoinTransactionRoute
import com.batro.ui.screen.editTransaction.lp.EditLpTransactionRoute
import com.batro.ui.screen.portfolio.PortfolioRoute
import com.batro.ui.screen.selectCoin.SelectCoinRoute
import com.batro.ui.screen.selectLpToken.SelectLpTokenRoute
import com.batro.ui.screen.selectPlatform.SelectPlatformRoute
import com.batro.ui.theme.CryptoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAppTheme {
                BatroApp()
            }
        }
    }
}

@Composable
fun BatroApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "portfolio") {
        composable("portfolio") {
            PortfolioRoute(navController)
        }
        composable(
            "addCoinTransaction?coinId={coinId}",
            arguments = listOf(navArgument("coinId") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            val coinId = it.arguments?.getString("coinId")
            AddTransactionRoute(coinId, navController)
        }
        composable(
            "addLpTransaction?lpAddress={lpAddress}",
            arguments = listOf(navArgument("lpAddress") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            val lpAddress = it.arguments?.getString("lpAddress")
            AddLpTransactionRoute(lpAddress, navController)
        }
        composable("selectCurrency") {
            SelectCoinRoute(navController)
        }
        composable("selectPlatform") {
            SelectPlatformRoute(navController)
        }
        composable(
            "portfolioDetails/{coinId}",
            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
        ) {
            val coinId = it.arguments?.getString("coinId") ?: return@composable
            DetailsCoinRoute(coinId, navController)
        }
        composable(
            "portfolioLpDetails/{lpAddress}",
            arguments = listOf(navArgument("lpAddress") { type = NavType.StringType })
        ) {
            val lpAddress = it.arguments?.getString("lpAddress") ?: return@composable
            DetailsLpRoute(lpAddress, navController)
        }
        composable(
            "portfolioEdit/{txId}",
            arguments = listOf(navArgument("txId") { type = NavType.IntType })
        ) {
            val txId = it.arguments?.getInt("txId") ?: return@composable
            EditCoinTransactionRoute(txId, navController)
        }
        composable(
            "portfolioLpEdit/{txId}",
            arguments = listOf(navArgument("txId") { type = NavType.IntType })
        ) {
            val txId = it.arguments?.getInt("txId") ?: return@composable
            EditLpTransactionRoute(txId, navController)
        }
        composable(
            "selectLpToken/{platformId}",
            arguments = listOf(navArgument("platformId") { type = NavType.StringType })
        ) {
            val txId = it.arguments?.getString("platformId") ?: return@composable
            SelectLpTokenRoute(txId, navController)
        }
    }
}
