package com.quere.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quere.domain.model.common.Bookmark

@Database(entities = [Bookmark::class], version = 1, exportSchema = false)

abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun bookmarkDao() : BookmarkDao

}