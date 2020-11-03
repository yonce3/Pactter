package com.yonce3.pactter.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.yonce3.pactter.data.local.AppDatabase
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.data.repository.AddPacRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AddPacRepository
    val allPacs: LiveData<List<Pac>>

    init {
        val pacDao = AppDatabase.getDatabase(application, viewModelScope).pacDao()
        repository = AddPacRepository(pacDao)
        allPacs = repository.allPacs
    }
}