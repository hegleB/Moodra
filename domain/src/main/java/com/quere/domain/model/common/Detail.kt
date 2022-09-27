package com.quere.domain.model.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Detail(
    val type :Boolean,
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
) : Parcelable
