package com.mylearning.devplacement.filemanager

import android.content.Context
import android.widget.Toast
import com.mylearning.devplacement.utils.Utility
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL


/* */
object FileDownloader {

// function to download csv
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
                val path = File(context.filesDir, "owners${File.separator}")
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

                }
            } catch (e: Exception) {
                println("Error Second Exception")
            }
        }.start()

    }

    // function to read csv file
    fun readCsv(context: Context): File {
        val path = File(context.filesDir, "Owners${File.separator}")
        return File(path, Utility.CAR_OWNER_DATA)
    }
}