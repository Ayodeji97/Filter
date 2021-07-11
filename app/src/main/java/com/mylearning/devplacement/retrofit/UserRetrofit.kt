package com.mylearning.devplacement.retrofit

import retrofit2.http.GET
/* user endpoint */
interface UserRetrofit {

    @GET("accounts")
    suspend fun getUsers() : List<UserNetworkEntity>
}