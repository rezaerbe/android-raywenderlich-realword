package com.erbe.petsavedesign.common.data.di

import com.erbe.petsavedesign.common.data.api.ApiConstants
import com.erbe.petsavedesign.common.data.api.PetFinderApi
import com.erbe.petsavedesign.common.data.api.interceptors.AuthenticationInterceptor
import com.erbe.petsavedesign.common.data.api.interceptors.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): PetFinderApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PetFinderApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authenticationInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }
}