package com.quere.domain.model.common

import java.io.Serializable

data class OtherContents(
    val id: Int,
    val results : List<OtherContent>

) : Serializable

data class OtherContent(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String? = "",
    val first_air_date : String? = "",
    val title: String? = "",
    val name: String? = "",
    val video: Boolean,
    val vote_average: String,
    val vote_count: Int,
) : Serializable