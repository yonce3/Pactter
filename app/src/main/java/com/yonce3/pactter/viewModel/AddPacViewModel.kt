package com.yonce3.pactter.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yonce3.pactter.data.local.AppDatabase
import com.yonce3.pactter.data.local.entity.Pac
import com.yonce3.pactter.repository.AddPacRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPacViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AddPacRepository

    init {
        val pacDao = AppDatabase.getDatabase(application, viewModelScope).pacDao()
        repository = AddPacRepository(pacDao)
    }

    fun insert(pac: Pac) = viewModelScope.launch (Dispatchers.IO) {
        repository.insertPac(pac)
    }

    fun getPacCount(): Int? = repository.getPacCount()

}