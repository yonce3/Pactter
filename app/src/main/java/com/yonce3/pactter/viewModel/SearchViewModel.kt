package com.yonce3.pactter.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonce3.pactter.data.entity.Article
import com.yonce3.pactter.data.repository.QiitaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    var articles: MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    var progressBarVisibility = MutableLiveData<Int>(View.GONE)
    private val qiitaRepository: QiitaRepository = QiitaRepository()

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    fun searchArticles(query: String?) {
        progressBarVisibility.postValue(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            articles.postValue(qiitaRepository.getArticles(query))
        }
    }

    fun clearArticles() {
        articles.postValue(listOf())
    }
}