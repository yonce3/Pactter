package com.yonce3.pactter.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yonce3.pactter.R
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.databinding.ActivityPacDetailBinding
import com.yonce3.pactter.viewModel.PacDetailViewModel


class PacDetailActivity : AppCompatActivity() {

    private val pacId: Int by lazy {
        val intent = intent
        intent.getIntExtra("pacId", 0)
    }

    private val viewModel: PacDetailViewModel by lazy {
        PacDetailViewModel.ViewModelFactory(application, pacId).create(PacDetailViewModel::class.java)
    }

    private lateinit var pac: Pac

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityPacDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_pac_detail)
        binding.also {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }

        // toolbarを設定
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }

        val favoriteButton: ImageView = findViewById(R.id.favorite_button)
        favoriteButton.setOnClickListener {
            favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        }

        val shareButton: ImageView = findViewById(R.id.share_button)
        shareButton.setOnClickListener {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}