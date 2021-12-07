package com.example.moviesimpleapp.retrofit

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
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

