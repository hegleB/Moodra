package com.quere.data.service.model

import android.os.Parcelable
import com.quere.domain.model.common.Genre
import kotlinx.parcelize.Parcelize


data class MovieResponse(
    val page: Integer,
    val results: List<Movie>

)

@Parcelize
data class Movie(
    val backdrop_path: String,
    val id: Int,
    val genres : List<Genre>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime : Int,
    val title: String,
    val video: Boolean,
    val vote_average: String,
    val vote_count: Int
): Parcelable