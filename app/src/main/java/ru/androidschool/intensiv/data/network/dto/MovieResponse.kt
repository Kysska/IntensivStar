package ru.androidschool.intensiv.data.network.dto

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName(value = "title", alternate = ["name"])
    val title: String?,
    @SerializedName("original_title", alternate = ["original_name"])
    val originalTitle: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_country")
    val originalCountry: List<String>?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName(value = "release_date", alternate = ["first_air_date"])
    val releaseDate: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}
