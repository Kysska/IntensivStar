package ru.androidschool.intensiv.data.network.dto

import com.google.gson.annotations.SerializedName

data class ProductionCountryResponse(
    @SerializedName("iso3166_1")
    val iso3166_1: String?,
    @SerializedName("name")
    val name: String?
)
