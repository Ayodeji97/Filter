package com.mylearning.devplacement.retrofit

import retrofit2.http.GET

interface UserRetrofit {

    @GET("accounts")
    suspend fun getUsers() : List<UserNetworkEntity>
}