package com.yonce3.pactter.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yonce3.pactter.R
import com.yonce3.pactter.util.REQUEST_ADD_PAC
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var floatingAddButton: FloatingActionButton
    lateinit var floatingDmButton: FloatingActionButton

    val homeFragment =  HomeFragment()
    val searchFragment =  SearchFragment()
    lateinit var transaction: FragmentTransaction
    // lateinit var notificationFragment: NotificationFragment
    // lateinit var

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        floatingAddButton = findViewById(R.id.add_pac_button)
        floatingAddButton.setOnClickListener {
            // TODO: アプリへの権限を確認
            if (true) {
                val intent = Intent(this, AddPacActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD_PAC)
            } else {
                requestExternalStorageWritable()
            }
        }

        // directMessageButton = findViewById(

        // bottomNavigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(onBottomNavigationClick)
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

    // 端末のストレージの書き込み権限を確認する
    private fun requestExternalStorageWritable() {
        //ExternalStorageAuthority.
    }

    class ExternalStorageAuthority() : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(context)
            builder.also {
//                it.setMessage("デバイス内の写真、メディア、ファイルへのアクセス")
//                it.setTitle()
                it.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

                })
                it.setNegativeButton("キャンセル", DialogInterface.OnClickListener { dialog, which ->
                    
                })
            }
            return super.onCreateDialog(savedInstanceState)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == RESULT_OK) {
//            when (requestCode) {
//                REQUEST_ADD_PAC ->
//            }
//        }
//    }
}