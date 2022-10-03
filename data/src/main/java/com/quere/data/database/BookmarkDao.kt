package com.quere.data.database

import androidx.room.*
import com.quere.domain.model.common.Bookmark

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark)

    @Query("select * from bookmark_table")
    fun getBookmark(): List<Bookmark>

    @Query("select * from bookmark_table where bookmark_table.id = :id")
    suspend fun checkBookmark(id: String): Bookmark

    @Query("delete from bookmark_table where bookmark_table.id = :id")
    suspend fun removeBookmark(id: String): Int

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("delete from bookmark_table")
    suspend fun deleteAll()

}