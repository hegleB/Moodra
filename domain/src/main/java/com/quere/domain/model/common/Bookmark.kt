package com.quere.domain.model.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bookmark_table")
class Bookmark(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var overview: String? = null,
    var isAdult: Boolean? = false,
    var poster_path: String? = null,
    var backdrop_path: String? = null,
    var relese_date: String? = null,
    var vote_average: String? = null,
) : Serializable