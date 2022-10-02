package com.quere.presenation.di

import android.content.Context
import androidx.room.Room
import com.quere.data.database.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        BookmarkDatabase::class.java,
        "bookmark_db"
    ).build()

    @Provides
    fun provideBookmarkDao(database: BookmarkDatabase) = database.bookmarkDao()

}