package com.yonce3.pactter.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.room.Room
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
    }
}