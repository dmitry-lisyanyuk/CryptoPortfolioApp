package com.batro.data.database.converters

import androidx.room.TypeConverter
import org.web3j.abi.datatypes.Address
import java.math.BigDecimal
import java.math.BigInteger

class BaseConverter {

    @TypeConverter
    fun toAddress(address: String?): Address? {
        return address?.let { Address(it) }
    }

    @TypeConverter
    fun fromAddress(address: Address?): String? {
        return address?.toString()
    }

    @TypeConverter
    fun fromBigDecimal(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    @TypeConverter
    fun toBigDecimal(bigDecimal: BigDecimal?): String? {
        return bigDecimal?.toPlainString()
    }

    @TypeConverter
    fun fromBigInteger(value: String?): BigInteger? {
        return value?.let { BigInteger(it) }
    }

    @TypeConverter
    fun toBigInteger(bigDecimal: BigInteger?): String? {
        return bigDecimal?.toString()
    }
}