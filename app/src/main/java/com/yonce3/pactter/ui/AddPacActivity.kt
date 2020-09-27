package com.yonce3.pactter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yonce3.pactter.R

class AddPacActivity : AppCompatActivity() {

    lateinit var addPacButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pac)

        addPacButton = findViewById(R.id.add_pac_button)
        addPacButton.setOnClickListener {
        }
    }
}