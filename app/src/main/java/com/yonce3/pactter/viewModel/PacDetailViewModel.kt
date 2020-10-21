package com.yonce3.pactter.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.yonce3.pactter.data.AppDatabase
import com.yonce3.pactter.data.entity.Pac
import com.yonce3.pactter.repository.AddPacRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PacDetailViewModel(application: Application, pacId: Int) : AndroidViewModel(application) {

    private lateinit var repository: AddPacRepository
    private var pac: LiveData<Pac> = MutableLiveData<Pac>()

    init {
        val pacDao = AppDatabase.getDatabase(application, viewModelScope).pacDao()
        pac = pacDao.findPacWithId(pacId)
        repository = AddPacRepository(pacDao)
    }

    class ViewModelFactory(application: Application, pacId: Int): ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return super.create(modelClass)
        }
    }
}

