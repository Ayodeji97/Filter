package com.mylearning.devplacement.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mylearning.devplacement.retrofit.UserRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder () : Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit (gson : Gson) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://android-json-test-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))

    }

    @Singleton
    @Provides
    fun provideBlogService (retrofit: Retrofit.Builder) : UserRetrofit {

        return retrofit
            .build()
            .create(UserRetrofit::class.java)

    }


}