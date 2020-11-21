package com.yonce3.pactter.ui

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.DISPLAY_NAME
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yonce3.pactter.R
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.viewModel.AddPacViewModel
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddPacActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AddPacActivity"
    }

    lateinit var addPacButton: Button
    lateinit var backButton: ImageView
    lateinit var pacText: EditText
    lateinit var cameraButton: FloatingActionButton
    lateinit var photo: ImageView
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_CAMERA_PERMISSION = 2
    var currentPhotoPath: String = ""
    private lateinit var addPacViewModel: AddPacViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pac)

        addPacViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(AddPacViewModel::class.java)

        pacText = findViewById(R.id.edit_text)
        // pacText.requestFocus()
        // TODO: 画面を開いたタイミングで、Edittextにフォーカス&キーボードが表示されるようにしたい
        // InputMethodManagerを取得
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(pacText.windowToken, 0)
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
        //inputMethodManager.showSoftInput(pacText, 0)

        // imageView
        photo = findViewById(R.id.pac_image)

        addPacButton = findViewById(R.id.add_pac_button)
        addPacButton.setOnClickListener {
            // DBに保存
            savePack(pacText.text.toString())
        }

        backButton = findViewById(R.id.buck_button)
        backButton.setOnClickListener {
            finish()
        }

        cameraButton = findViewById(R.id.camera_button)
        cameraButton.setOnClickListener {
            val cameraPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            val cameraRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )
            if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else if (cameraRationale) {
                showRequestCameraPermission()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    // TODO: フォトアプリに画像を保存する方法
                    val contentValues = ContentValues().apply {
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            put(MediaStore.Images.Media.RELATIVE_PATH, "Picture/sample")
                            put(MediaStore.Images.Media.IS_PENDING, true)
                        }
                        put("_data", currentPhotoPath)
                    }
                    val externalStorageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                    } else {
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }

                    val imageUri: Uri = contentResolver.insert(externalStorageUri, contentValues)!!

                    contentResolver.openOutputStream(imageUri).use { out ->
                        // val bitmap .compress(Bitmap.CompressFormat.JPEG, 90, out)
                    }

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, false)
                    contentResolver.update(imageUri, contentValues, null, null)

                    //val bitmap =BitmapFactory.
                    val inputStream = contentResolver.openOutputStream(imageUri).use { out ->

                    }
//                    val bitmap: Bitmap = BitmapFactory.
//                    photo.setImageBitmap(bitmap)
                    photo.visibility = View.VISIBLE
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e(TAG, " Error occurred while creating the File: createImageFile() AddPac")
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.pactter.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun showRequestCameraPermission() {
        AlertDialog.Builder(this)
            .setMessage("デバイスの「設定」でカメラの権限を許可してください。")
            .setPositiveButton("OK") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.yonce3.pactter"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .setNegativeButton("キャンセル") {_, _ -> }
            .create().show()
    }

    fun savePack(pacText: String): Boolean {
        if (pacText.isNotBlank()) {
            addPacViewModel.insert(Pac(0, pacText, currentPhotoPath))
            finish()
            return true
        } else {
            Toast.makeText(this, R.string.input_text_alert, Toast.LENGTH_SHORT).show()
            return false
        }
    }
}