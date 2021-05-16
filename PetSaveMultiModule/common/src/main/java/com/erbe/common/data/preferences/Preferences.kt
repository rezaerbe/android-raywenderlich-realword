package com.erbe.common.data.preferences

interface Preferences {

    fun putToken(token: String)

    fun putTokenExpirationTime(time: Long)

    fun putTokenType(tokenType: String)

    fun getToken(): String

    fun getTokenExpirationTime(): Long

    fun getTokenType(): String

    fun deleteTokenInfo()

    fun getPostcode(): String

    fun putPostcode(postcode: String)

    fun getMaxDistanceAllowedToGetAnimals(): Int

    fun putMaxDistanceAllowedToGetAnimals(distance: Int)
}