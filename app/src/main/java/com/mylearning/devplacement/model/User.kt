package com.mylearning.devplacement.model

import com.mylearning.devplacement.room.Colors

// domain model
data class User(
        var id: String,
        var name: String,
        var image: String,
        var gender: String,
        var countries: Colors,
        var colors: Colors,
        var date: String
    )