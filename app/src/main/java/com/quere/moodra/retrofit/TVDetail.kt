package com.quere.moodra.retrofit

data class TVDetail(
    val backdrop_path: String? = null,
    val genres: List<Genre>? = null,
    val id: Integer? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val first_air_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null
)
