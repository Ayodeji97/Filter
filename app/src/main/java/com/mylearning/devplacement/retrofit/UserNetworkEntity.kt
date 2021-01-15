package com.mylearning.devplacement.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
// data class of network entity from the end point
data class UserNetworkEntity(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("fullName")
    @Expose
    var name: String?,

    @SerializedName("avatar")
    @Expose
    var image: String?,

    @SerializedName("gender")
    @Expose
    var gender: String?,

    @SerializedName("countries")
    @Expose
    var countries: List<String>?,

    @SerializedName("colors")
    @Expose
    var colors: List<String>?,

    @SerializedName("createdAt")
    @Expose
    var date: String?,


    )