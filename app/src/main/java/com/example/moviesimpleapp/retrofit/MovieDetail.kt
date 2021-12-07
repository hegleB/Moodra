package com.example.moviesimpleapp.retrofit

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val backdrop_path: String? = null,
    val genres: List<Genre>? = null,
    val id: Integer? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val runtime: Int? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null
)