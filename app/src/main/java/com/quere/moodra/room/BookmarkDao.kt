package com.quere.moodra.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark_table")
    fun getBookmark(): LiveData<List<Bookmark>>

    @Query("select count(*) from bookmark_table where bookmark_table.id = :id")
    suspend fun checkBookmark(id: String): Int

    @Query("delete from bookmark_table where bookmark_table.id = :id")
    suspend fun removeBookmark(id: String): Int

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("delete from bookmark_table")
    suspend     fun deleteAll()

}