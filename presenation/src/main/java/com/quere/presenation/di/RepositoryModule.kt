package com.quere.presenation.di

import com.quere.data.repository.remote.common.CommonRepositoryImpl
import com.quere.data.repository.remote.movie.MovieRepositoryImpl
import com.quere.data.repository.remote.tv.TVshowRepositoryImpl
import com.quere.data.service.commonservice.CommonService
import com.quere.data.service.movieservice.MovieService
import com.quere.data.service.tvservice.TVService
import com.quere.domain.repository.common.CommonRepository
import com.quere.domain.repository.movie.MovieRepository
import com.quere.domain.repository.tv.TVshowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(
        movieService: MovieService
    ) : MovieRepository {
        return MovieRepositoryImpl(
            movieService
        )
    }

    @Provides
    fun provideTVshowRepository(
        tvService: TVService
    ) : TVshowRepository {
        return TVshowRepositoryImpl(
            tvService
        )
    }

    @Provides
    fun provideCommonRepository(
        commonService: CommonService
    ) : CommonRepository {
        return CommonRepositoryImpl(
            commonService
        )
    }
}