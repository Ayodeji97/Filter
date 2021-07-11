package com.mylearning.devplacement.model

typealias CarOwnerList = ArrayList<CarOwner>
// car owner list data class for csv
data class CarOwner(
        val id: Long,
        val image: Int,
        val firstName: String,
        val lastName: String,
        val email: String,
        val country: String,
        val carModel: String,
        val year: String,
        val carColor: String,
        val gender: String,
        val jobTitle: String,
        val bio: String

)