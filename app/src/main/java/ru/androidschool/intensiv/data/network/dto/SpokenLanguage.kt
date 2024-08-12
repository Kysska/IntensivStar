package ru.androidschool.intensiv.data.network.dto

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso639_1")
    val iso639_1: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("englishName")
    val englishName: String?
)
