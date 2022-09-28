package com.quere.domain.model.common

import java.io.Serializable

data class Detail(
    val type :String = "",
    val title : String? = "",
    val name : String?= "",
    val genres: List<Genre>?= null,
    val id : Int?= null,
    val overview : String? = "",
    val is_adult : Boolean?=false,
    val poster_path : String?="",
    val backdrop_path: String?="",
    val release_date : String?="",
    val runtime: Int? = null,
    val video : Boolean?=false,
    val vote_average : String?=""
) : Serializable
