package ru.androidschool.intensiv.data.network.dto

import com.google.gson.annotations.SerializedName

data class CreditResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val castsList: List<CastResponse>?
)
