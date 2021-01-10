package com.mylearning.devplacement.model

// domain model
data class User (
    var id : String,
    var name : String,
    var image : String,
    var gender : String,
    var countries : List<String>,
    var colors : List<String>,
    var date : String
    )