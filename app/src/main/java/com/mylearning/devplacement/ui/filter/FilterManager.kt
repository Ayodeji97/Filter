package com.mylearning.devplacement.ui.filter

import android.location.Criteria
import com.mylearning.devplacement.R
import com.mylearning.devplacement.model.CarOwner
import com.mylearning.devplacement.model.CarOwnerList
import com.mylearning.devplacement.model.User
import com.mylearning.devplacement.utils.Utility
import com.mylearning.devplacement.utils.Utility.CSVHeader.BIO
import com.mylearning.devplacement.utils.Utility.CSVHeader.CAR_COLOR
import com.mylearning.devplacement.utils.Utility.CSVHeader.CAR_MODEL
import com.mylearning.devplacement.utils.Utility.CSVHeader.COUNTRY
import com.mylearning.devplacement.utils.Utility.CSVHeader.EMAIL
import com.mylearning.devplacement.utils.Utility.CSVHeader.FIRST_NAME
import com.mylearning.devplacement.utils.Utility.CSVHeader.GENDER
import com.mylearning.devplacement.utils.Utility.CSVHeader.ID
import com.mylearning.devplacement.utils.Utility.CSVHeader.JOB_TITLE
import com.mylearning.devplacement.utils.Utility.CSVHeader.LAST_NAME
import com.mylearning.devplacement.utils.Utility.CSVHeader.YEAR
import de.siegmar.fastcsv.reader.CsvReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/* Class responsible for IO operation*/

class FilterManager {

    suspend fun readFile (absoluteFile : File) : CarOwnerList {

        val result = CarOwnerList()

        withContext(Dispatchers.IO) {
            try {
                val csvReader = CsvReader()
                csvReader.setFieldSeparator(',')
                csvReader.setSkipEmptyRows(true)
                csvReader.setContainsHeader(true)
                csvReader.parse(BufferedReader(FileReader(absoluteFile.absolutePath)))
                        .use { csvParser ->
                            while (true) {
                                val row = csvParser.nextRow() ?: break
                                result.add(
                                        CarOwner(
                                                row.getField(ID).toLong(),
                                                R.drawable.car,
                                                row.getField(FIRST_NAME),
                                                row.getField(LAST_NAME),
                                                row.getField(EMAIL),
                                                row.getField(COUNTRY),
                                                row.getField(CAR_MODEL),
                                                row.getField(YEAR),
                                                row.getField(CAR_COLOR),
                                                row.getField(GENDER),
                                                row.getField(JOB_TITLE),
                                                row.getField(BIO)
                                        )
                                )
                            }
                        }

            } catch (e : Exception) {
                Timber.e(e.printStackTrace().toString())
            }
        }

        return result
    }

    suspend fun filterItem (carOwnerList: CarOwnerList, user: User) : CarOwnerList {

        var filterResult = CarOwnerList()

        // algorithm to filter

        return filterResult

    }



}