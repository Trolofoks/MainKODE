package com.honey.mainkode.di

import com.honey.repository.PeopleRepositoryImpl
import com.honey.repository.PeoplesRepository
import com.honey.sourse.external.Endpoints
import com.honey.sourse.external.KodeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val modifiedRequest = originalRequest.newBuilder()
                    .header("Accept", "application/json, application/xml")
                    .build()
                chain.proceed(modifiedRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideKodeApi(retrofit: Retrofit): KodeApi {
        return retrofit.create(KodeApi::class.java)
    }

    @Provides
    @Singleton
    fun providePeopleRepository(kodeApi: KodeApi) : PeoplesRepository{
        return PeopleRepositoryImpl(api = kodeApi)
    }
}