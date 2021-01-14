package com.mylearning.devplacement.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL



object FileDownloader {


    fun downloadCsv(
            fileUrl: String,
            context: Context
    ) {

        Thread {
            val fileUrl = URL(fileUrl)
            val connection = fileUrl.openConnection() as HttpURLConnection
            connection.doInput = true
            try {
                connection.connect()
                val inputStream = connection.inputStream
                val path = File(context.filesDir, "Owners${File.separator}")
                println("Owners${File.separator}")
                if (!path.exists()) {
                    println("Owners${File.separator}")
                    path.mkdirs();
                }
                val outFile = File(path, Utility.CAR_OWNER_DATA)
                val outputStream = FileOutputStream(outFile)
                try {
                    outputStream.use { output ->
                        val buffer = ByteArray(4 * 1024)
                        var byteCount = inputStream.read(buffer)
                        while (byteCount > 0) {
                            output.write(buffer, 0, byteCount)
                            byteCount = inputStream.read(buffer)
                        }
                        output.flush()
                        output.close()
                    }



                } catch (e: FileNotFoundException) {
                    println("Error File Not found")

//                    context.runOnUiThread {
//                        Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
//                    }
                }
            } catch (e: Exception) {
                println("Error Second Exception")

                //Handle Error in case where there's no internet connection and no profile image saved.
//                context.runOnUiThread {
//                    Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
//                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
//                }
            }
        }.start()

    }


    fun readCsv(context: Context): File {
        val path = File(context.filesDir, "Owners${File.separator}")
        return File(path, Utility.CAR_OWNER_DATA)
    }
}