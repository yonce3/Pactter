package com.yonce3.pactter.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.yonce3.pactter.data.local.AppDatabase
import com.yonce3.pactter.data.local.entity.Pac
import com.yonce3.pactter.repository.AddPacRepository

class PacDetailViewModel(application: Application, pacId: Int) : AndroidViewModel(application) {

    private lateinit var repository: AddPacRepository
    var pac: LiveData<Pac> = MutableLiveData<Pac>()

    init {
        val pacDao = AppDatabase.getDatabase(application, viewModelScope).pacDao()
        repository = AddPacRepository(pacDao)
        pac = repository.findPacWithId(pacId)

//        viewModelScope.launch {
//            try {
////                coroutineScope {
//                    // pac = repository.findPacWithId(pacId)
////                    val pacData: Deferred<LiveData<Pac>> = async {
////                        repository.findPacWithId(pacId)
////                    }
////                    pac = pacData.await()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
    }

    class ViewModelFactory(private val application: Application, private val pacId: Int): ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return when(modelClass) {
                PacDetailViewModel::class.java -> PacDetailViewModel(application, pacId)

                else -> throw IllegalArgumentException("Unknown ViewModel class")
            } as T
        }
    }
}

