package com.yonce3.pactter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yonce3.pactter.R
import com.yonce3.pactter.data.AppDatabase

class MainActivity : AppCompatActivity() {
    lateinit var floatingAddButton: FloatingActionButton
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // toolbarの設定
        setSupportActionBar(findViewById(R.id.toolbar))

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

        // bottomNavigation
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(onBottomNavigationClick)

        // TODO: あとで削除
        var db = Room.databaseBuilder(
            this, AppDatabase::class.java, "database-name").build()
        db.userDao()
    }

    private val onBottomNavigationClick = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.home -> {
                // TODO: fragmentを表示
                println("home")
                floatingAddButton.setImageResource(R.mipmap.outline_add_white_36)
                true
            }
            R.id.search -> {
                floatingAddButton.setImageResource(R.mipmap.outline_add_white_36)
                true
            }
            R.id.notification -> {
                floatingAddButton.setImageResource(R.mipmap.outline_add_white_36)
                true
            }
            R.id.mail -> {
                floatingAddButton.setImageResource(R.mipmap.ic_launcher_round)
                true
            }
            else -> false
        }
    }
}