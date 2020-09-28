package com.yonce3.pactter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.yonce3.pactter.R

class AddPacActivity : AppCompatActivity() {

    lateinit var addPacButton: Button
    lateinit var backButton: ImageView

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
    }
}