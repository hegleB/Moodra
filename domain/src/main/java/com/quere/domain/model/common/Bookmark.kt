package com.quere.domain.model.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bookmark_table")
data class Bookmark(

    @PrimaryKey
    val id : Int?= null,
    val type :String = "",
    val title : String? = "",
    val name : String?= "",
    val overview : String? = "",
    val is_adult : Boolean?=false,
    val poster_path : String?="",
    val backdrop_path: String?="",
    val release_date : String?="",
    val runtime: Int? = null,
    val video : Boolean?=false,
    val vote_average : String?=""
)   : Serializable
