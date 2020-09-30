package com.yonce3.pactter.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.yonce3.pactter.R
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

        addPacButton = findViewById(R.id.add_pac_button)
        addPacButton.setOnClickListener {
            // DBに保存
        }

        backButton = findViewById(R.id.buck_button)
        backButton.setOnClickListener {
            finish()
        }

        pacText = findViewById(R.id.edit_text)
        // pacText.requestFocus()
        // InputMethodManagerを取得
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(pacText.windowToken, 0)
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
        //inputMethodManager.showSoftInput(pacText, 0)
    }
}