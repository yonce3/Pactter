package com.yonce3.pactter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yonce3.pactter.R
import com.yonce3.pactter.data.AppDatabase

class MainActivity : AppCompatActivity() {
    lateinit var floatingAddButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // homeのフラグメントを追加
        val homeFragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, homeFragment)
        transaction.commit()

        // Pac追加ボタン
        floatingAddButton = findViewById(R.id.add_pac_button)
        floatingAddButton.setOnClickListener {
            val intent = Intent(this, AddPacActivity::class.java)
            startActivity(intent)
        }

        // TODO: あとで削除
        var db = Room.databaseBuilder(
            this, AppDatabase::class.java, "database-name").build()
        db.userDao()
    }
}