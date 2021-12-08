package com.quere.moodra.retrofit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trailers(
    val id: Integer,
    val results: List<Trailer>

) : Parcelable

@Parcelize
data class Trailer(
    val name: String,
    val key: String,
    val site: String,
    val size: Integer,
    val type: String,
    val id: String
) : Parcelable

