package com.yonce3.pactter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yonce3.pactter.R
import com.yonce3.pactter.data.AppDatabase
import com.yonce3.pactter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var floatingAddButton: FloatingActionButton
    lateinit var floatingDmButton: FloatingActionButton

    private lateinit var binding: ActivityMainBinding

    val homeFragment =  HomeFragment()
    val searchFragment =  SearchFragment()
    lateinit var transaction: FragmentTransaction
    // lateinit var notificationFragment: NotificationFragment
    // lateinit var

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // toolbarの設定
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""
        // TODO: toolbarにアイコンを設定する方法
        // supportActionBar?.setLogo(R.mipmap.outline_clear_black_36)

        val fragmentList = listOf<Fragment>(homeFragment, searchFragment)
        fragmentList.map {
            transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, it)
            if (it == searchFragment) {
                transaction.hide(it)
            } else {
                transaction.show(it)
            }
            transaction.commit()
        }

        // Pac追加ボタン
        floatingAddButton = binding.addPacButton
        floatingAddButton.setOnClickListener {
            val intent = Intent(this, AddPacActivity::class.java)
            startActivity(intent)
        }

        // directMessageButton = findViewById(

        // bottomNavigation
        binding.bottomNavigation.setOnNavigationItemSelectedListener(onBottomNavigationClick)

        // TODO: あとで削除
        var db = Room.databaseBuilder(
            this, AppDatabase::class.java, "database-name").build()
        db.userDao()
    }

    private val onBottomNavigationClick = BottomNavigationView.OnNavigationItemSelectedListener {
        val transaction = supportFragmentManager.beginTransaction()
        when (it.itemId) {
            R.id.home -> {
                // TODO: fragmentを表示
                if (homeFragment.isHidden) {
                    transaction.hide(searchFragment)
                    transaction.show(homeFragment).commit()
                } else {
                    transaction.hide(searchFragment).commit()
                }
//
//                transaction.add(R.id.fragment_container, HomeFragment())
//                transaction.commit()
                floatingAddButton.setImageResource(R.mipmap.outline_add_white_36)
                true
            }
            R.id.search -> {
                if (searchFragment.isHidden) {
                    transaction.show(searchFragment)
                    transaction.hide(homeFragment).commit()
                } else {
                    transaction.hide(homeFragment).commit()
                }

//                transaction.add(R.id.fragment_container, SearchFragment())
//                transaction.commit()
                floatingAddButton.setImageResource(R.mipmap.outline_add_white_36)
                true
            }
            R.id.notification -> {
//                transaction.add(R.id.fragment_container, SearchFragment())
//                transaction.commit()
                floatingAddButton.setImageResource(R.mipmap.outline_add_white_36)
                true
            }
            R.id.mail -> {
//                transaction.add(R.id.fragment_container, SearchFragment())
//                transaction.commit()
                floatingAddButton.setImageResource(R.mipmap.ic_launcher_round)
                true
            }
            else -> false
        }
    }
}