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
import com.yonce3.pactter.util.ShowToast
import com.yonce3.pactter.viewModel.AddPacViewModel
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddPacActivity : AppCompatActivity() {

    @Inject lateinit var showToast: ShowToast
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

        // Dagger2の初期化

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
                val storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (storagePermission == PackageManager.PERMISSION_GRANTED) {
                    startCamera()
                    // startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE)
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
                }
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
                        // マイムの設定
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                        put(MediaStore.Images.Media.DISPLAY_NAME, "Paccter_photo.jpg")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Pactter")
                            // 書き込み時に、メディアへのアクセスを排他制御する
                            put(MediaStore.Images.Media.IS_PENDING, true)
                        }
                    }
                    val externalStorageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                    } else {
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }

                    val imageUri: Uri = contentResolver.insert(externalStorageUri, contentValues)!!

                    val imageBitmap = BitmapFactory.decodeFile(currentPhotoPath)
                    contentResolver.openOutputStream(imageUri).use { out ->
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                    }

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, false)
                    contentResolver.update(imageUri, contentValues, null, null)

//                    val inputStream = FileInputStream(File(currentPhotoPath))
//                    val bitmap =BitmapFactory.decodeStream(inputStream)
                    photo.setImageBitmap(imageBitmap)
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
                //startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // TODO: 外部ストレージ内に保存する場合
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
            "Paccter_${timeStamp}_", /* prefix */
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

    private fun showRequestStoragePermission() {
        AlertDialog.Builder(this)
            .setMessage("デバイスの「設定」でストレージの権限を許可してください。")
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
            showToast.show(this, R.string.input_text_alert, Toast.LENGTH_SHORT)
            return false
        }
    }

    fun isExternalStorageWritable(): Boolean {
        var state = Environment.getExternalStorageState()
        return (Environment.MEDIA_MOUNTED.equals(state))
    }
}