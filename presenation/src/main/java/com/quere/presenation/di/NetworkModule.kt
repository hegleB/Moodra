package com.quere.presenation.di




import com.quere.data.service.commonservice.CommonService
import com.quere.data.service.movieservice.MovieService
import com.quere.data.service.tvservice.TVService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val TIME_OUT_COUNT : Long = 10

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_COUNT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_COUNT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url

            request.url(
                originalHttpUrl.newBuilder()
                    .addQueryParameter("language","ko-KR")
                    .addQueryParameter("api_key", "93852933922b9db90a3b1f240b5d5d96")
                    .addQueryParameter("region","KR")
                    .build()
            )

            chain.proceed(request.build())
        }
    }

    @Provides
    fun provideMovieApi(retrofit: Retrofit) : MovieService {
        return retrofit.create()
    }

    @Provides
    fun provideTVApi(retrofit: Retrofit) : TVService {
        return retrofit.create()
    }

    @Provides
    fun provideCommonApi(retrofit: Retrofit) : CommonService {
        return retrofit.create()
    }


}