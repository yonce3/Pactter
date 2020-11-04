package com.yonce3.pactter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonce3.pactter.data.entity.Article
import com.yonce3.pactter.data.repository.QiitaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    var articles: LiveData<List<Article>> = MutableLiveData<List<Article>>()
    private val qiitaRepository: QiitaRepository = QiitaRepository()

    fun searchArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            //articles = qiitaRepository.getArticles()
        }
    }
}