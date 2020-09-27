package com.yonce3.pactter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yonce3.pactter.R
import com.yonce3.pactter.viewModel.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, homeFragment)
    }
}