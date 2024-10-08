package ru.androidschool.intensiv.data.network.dto

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig

data class CastResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("cast_id")
    val castId: Int?,
    @SerializedName("character")
    val character: String?,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("order")
    val order: Int?
) {
    @SerializedName("profile_path")
    var profilePath: String? = null
        get() = "${BuildConfig.IMAGE_URL}$field"
}
