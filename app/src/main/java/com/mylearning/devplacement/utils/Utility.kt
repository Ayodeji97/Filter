package com.mylearning.devplacement.utils

/* File responsible for storing storing storing csv file details */
object Utility {

    const val FOLDER = "owners"
    const val DOWNLOAD_URL = "https://drive.google.com/u/0/uc?id=1giBv3pK6qbOPo0Y02H-wjT9ULPksfBCm&export=download"
    const val CAR_OWNER_DATA = "car_ownsers_data.csv"

    object CSVHeader {
        const val ID: Int = 0
        const val FIRST_NAME = 1
        const val LAST_NAME = 2
        const val EMAIL = 3
        const val COUNTRY = 4
        const val CAR_MODEL = 5
        const val YEAR = 6
        const val CAR_COLOR = 7
        const val GENDER = 8
        const val JOB_TITLE = 9
        const val BIO = 10
    }
}