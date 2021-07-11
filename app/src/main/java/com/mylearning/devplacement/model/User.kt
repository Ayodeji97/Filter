package com.mylearning.devplacement.model

import android.os.Parcelable
import com.mylearning.devplacement.room.Colors
import java.io.Serializable

// domain model
data class User(
        var id: String,
        var name: String?,
        var image: String?,
        var gender: String?,
        var countries: Colors?,
        var colors: Colors?,
        var date: String?
    ) : Serializable