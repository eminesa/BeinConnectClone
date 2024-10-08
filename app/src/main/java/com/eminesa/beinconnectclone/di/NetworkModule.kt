package com.eminesa.beinconnectclone.di

import com.eminesa.beinconnectclone.BuildConfig
import com.eminesa.beinconnectclone.BuildConfig.BASE_URL
import com.eminesa.beinconnectclone.data.source.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): MovieService {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(
            Interceptor { chain ->
                val currentUrl = chain.request().url
                val newUrl =
                    currentUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY)
                        .build()
                val currentRequest = chain.request().newBuilder()
                val newRequest = currentRequest.url(newUrl).build()
                chain.proceed(newRequest)

                /*val original = chain.request()
                val url = original.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
                val requestBuilder = original.newBuilder().url(url)
                chain.proceed(requestBuilder.build()) */
            },
        )
        .build()

}