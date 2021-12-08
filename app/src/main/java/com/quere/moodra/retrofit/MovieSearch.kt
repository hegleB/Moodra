package com.quere.moodra.retrofit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class search_movie(
    //SerializedName :
    val page: String,
    val results: List<MovieSearch>

):Parcelable


@Parcelize
data class MovieSearch(
    val original_title: String? = null,
    val title: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val adult: Boolean? = false,
    val release_date: String? = null,
    val vote_average: String? = null,
    val backdrop_path: String? = null,
    val id: Int? = 0,
    val runtime: Int? = 0
):Parcelable
