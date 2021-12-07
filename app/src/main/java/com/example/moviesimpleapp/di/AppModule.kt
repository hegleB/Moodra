package com.example.moviesimpleapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.retrofit.MediaService
import com.example.moviesimpleapp.room.BookmarkDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(
        context,
        BookmarkDatabase::class.java,
        "bookmark_db"
    ).build()

    @Provides
    @Singleton
    fun provideBookmarkDao(database: BookmarkDatabase) = database.bookmarkDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(AppConstants.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRetrofitApi(retrofit: Retrofit): MediaService =
        retrofit.create(MediaService::class.java)



}