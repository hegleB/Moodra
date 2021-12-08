package com.quere.moodra.retrofit


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVshows(
    //SerializedName :
    val page: String,
    val results: List<TVshow>
) :Parcelable

@Parcelize
data class TVshow(
    val name: String?,
    val original_language: String?,
    val overview: String?,
    val poster_path: String?,
    val vote_average: String?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val id: Int,

) : Parcelable