package com.quere.domain.model.tv

import android.os.Parcelable
import com.quere.domain.model.common.Genre
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVshows(

    val page: String,
    val results: List<TVshow>
) : Parcelable

@Parcelize
data class TVshow(
    val name: String?,
    val episode_run_time: List<Int> = listOf(),
    val genres: List<Genre> = listOf(),
    val original_language: String?,
    val overview: String?,
    val poster_path: String?,
    val vote_average: String?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val video: Boolean,
    val id: Int,

    ) : Parcelable