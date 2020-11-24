package com.yonce3.pactter.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.yonce3.pactter.BuildConfig
import java.io.File
import java.io.IOException
import java.lang.String.format
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FileUtil {

    fun createTempUri(file: File, context: Context): Uri {
        //val captureFile = createOutputFile(context)
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file)
    }

    fun createOutputFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val tempFile = File(context.filesDir, "images")
        val newFile = File(tempFile, "$timeStamp.jpg")
        if (!newFile.exists()) {
            try {
                newFile.parentFile.mkdirs()
                newFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return newFile
    }
}