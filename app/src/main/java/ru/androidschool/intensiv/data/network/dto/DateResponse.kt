package ru.androidschool.intensiv.data.network.dto

import com.google.gson.annotations.SerializedName

data class DateResponse(
    @SerializedName("maximum")
    val max: String,
    @SerializedName("minimum")
    val min: String
)
