package com.quere.moodra.di

import android.content.Context
import androidx.room.Room
import com.quere.moodra.room.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        BookmarkDatabase::class.java,
        "bookmark_db"
    ).build()

    @Provides
    @Singleton
    fun provideBookmarkDao(database: BookmarkDatabase) = database.bookmarkDao()
}