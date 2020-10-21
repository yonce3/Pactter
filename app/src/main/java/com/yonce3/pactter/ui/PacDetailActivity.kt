package com.yonce3.pactter.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yonce3.pactter.R
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.databinding.ActivityPacDetailBinding
import com.yonce3.pactter.viewModel.HomeViewModel
import com.yonce3.pactter.viewModel.PacDetailViewModel
import kotlinx.coroutines.launch


class PacDetailActivity : AppCompatActivity() {

    private val pacId: Int by lazy {
        val intent = intent
        intent.getIntExtra("pacId", 0)
    }

    private lateinit var pac: Pac

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPacDetailBinding>(this, R.layout.activity_pac_detail)
        binding.lifecycleOwner = this
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }

        // val viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(PacDetailViewModel::class.java)
        val viewModel = PacDetailViewModel.ViewModelFactory(application, pacId).create(PacDetailViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}