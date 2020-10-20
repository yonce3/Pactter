package com.yonce3.pactter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yonce3.pactter.R

class PacDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pac_detail)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}