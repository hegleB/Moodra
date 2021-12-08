package com.quere.moodra.retrofit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVTrailers(
    val id: Int,
    val tvTrailerList: List<TVTrailer>

) : Parcelable

@Parcelize
data class TVTrailer(
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val id: String
) : Parcelable
