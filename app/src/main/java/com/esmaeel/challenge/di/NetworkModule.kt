package com.esmaeel.challenge.di

import com.esmaeel.challenge.BuildConfig
import com.esmaeel.challenge.common.base.INJECT_API_KEY
import com.esmaeel.challenge.data.remote.PlacesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit.Builder): PlacesService = retrofit
        .baseUrl(BuildConfig.FOURSQUARE_BASE_URL)
        .build()
        .create(PlacesService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )

    @Provides
    @Singleton
    fun provideAuthInjectionInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val method = chain.request().tag(Invocation::class.java)!!.method()
            val builder = original.newBuilder()
            if (method.isAnnotationPresent(INJECT_API_KEY::class.java)) {
                builder.header(PlacesService.AUTHORIZATION, BuildConfig.API_KEY)
            }
            chain.proceed(builder.method(original.method, original.body).build())
        }
    }


    @Provides
    @Singleton
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
        keysInjector: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(keysInjector)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()
    }

}
