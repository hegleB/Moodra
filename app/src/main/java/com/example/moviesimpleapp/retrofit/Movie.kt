package com.example.moviesimpleapp.retrofit


import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movies(
    //SerializedName :
    val page: Integer,
    val results: List<Movie>

) : Parcelable

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
) : Parcelable
