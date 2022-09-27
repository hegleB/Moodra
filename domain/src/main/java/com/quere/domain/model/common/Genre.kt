package com.quere.domain.model.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genres(
    val id: Integer,
    val results: List<Detail>
) : Parcelable


@Parcelize
data class Genre(
    val id: Int,
    val name: String?
) : Parcelable