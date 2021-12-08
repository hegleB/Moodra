package com.quere.moodra.retrofit


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class search_tvshow(
    //SerializedName :
    val page: String,
    val results: List<TVshowSearch>

) : Parcelable

@Parcelize
data class TVshowSearch(
    val name: String?,
    val original_language: String?,
    val overview: String?,
    val poster_path: String?,
    val vote_average: String?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val id: Int,
    val runtime: Int

) : Parcelable
