package com.yonce3.pactter.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yonce3.pactter.R
import com.yonce3.pactter.data.AppDatabase
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.viewModel.AddPacViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddPacActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AddPacActivity"
        fun newInstance() = AddPacViewModel()
    }

    lateinit var addPacButton: Button
    lateinit var backButton: ImageView
    lateinit var pacText: EditText
    lateinit var cameraButton: FloatingActionButton
    lateinit var photo: ImageView
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_CAMERA_PERMISSION = 2
    lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pac)

        // TODO: あとで削除
        var db = Room.databaseBuilder(
            this, AppDatabase::class.java, "database-name").build()

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
            if (pacText.text.isNotBlank()) {
                db.pacDao().insert(Pac(0, pacText.text.toString(), ""))
                db.pacDao().insert(Pac(1, pacText.text.toString(), ""))

                finish()
            } else {
                Toast.makeText(this, R.string.input_text_alert, Toast.LENGTH_SHORT).show()
            }
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
                AlertDialog.Builder(this)
                    .setMessage("デバイスの「設定」でカメラの権限を許可してください。")
                    .setPositiveButton("OK") { _, _ ->
                        // TODO: アプリの設定画面に遷移
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.yonce3.pactter"))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                    .setNegativeButton("キャンセル") {_, _ -> }
                    .create().show()
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
                    // TODO: フルサイズの画像を取得する方法
                    val imageBitmap = data?.extras?.get("data") as Bitmap
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
}