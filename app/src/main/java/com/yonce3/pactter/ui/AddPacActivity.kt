package com.yonce3.pactter.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yonce3.pactter.R
import com.yonce3.pactter.data.AppDatabase
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.viewModel.AddPacViewModel

class AddPacActivity : AppCompatActivity() {

    companion object {
        fun newInstance() = AddPacViewModel()
    }

    lateinit var addPacButton: Button
    lateinit var backButton: ImageView
    lateinit var pacText: EditText
    lateinit var cameraButton: FloatingActionButton
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_CAMERA_PERMISSION = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pac)

        // TODO: あとで削除
        var db = Room.databaseBuilder(
            this, AppDatabase::class.java, "database-name").build()

        addPacButton = findViewById(R.id.add_pac_button)
        addPacButton.setOnClickListener {
            // DBに保存
            db.pacDao().insert(Pac(0, pacText.text.toString()))
            db.pacDao().insert(Pac(1, pacText.text.toString()))

            finish()
        }

        backButton = findViewById(R.id.buck_button)
        backButton.setOnClickListener {
            finish()
        }

        pacText = findViewById(R.id.edit_text)
        // pacText.requestFocus()
        // TODO: 画面を開いたタイミングで、Edittextにフォーカス&キーボードが表示されるようにしたい
        // InputMethodManagerを取得
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(pacText.windowToken, 0)
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
        //inputMethodManager.showSoftInput(pacText, 0)

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
                    // TODO: 撮影したDataを受け取る
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
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}