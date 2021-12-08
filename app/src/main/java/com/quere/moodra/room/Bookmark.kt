package com.quere.moodra.room



import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "bookmark_table")
@Parcelize
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
) :Parcelable