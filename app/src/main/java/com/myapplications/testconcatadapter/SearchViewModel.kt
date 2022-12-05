package com.myapplications.testconcatadapter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class SearchViewModel() : ViewModel() {
    private var arrayListRecentCurrencies: ArrayList<Currency> = ArrayList()
    private var arrayListRestCurrencies: ArrayList<Currency> = ArrayList()
    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedRecentCurrencies: MutableStateFlow<List<Currency>> = MutableStateFlow(arrayListOf())
    private var matchedRestCurrencies: MutableStateFlow<List<Currency>> = MutableStateFlow(arrayListOf())

    val userSearchModelState = combine(
        searchText,
        matchedRecentCurrencies,
        matchedRestCurrencies,
        showProgressBar
    ) { searchText, matchedRecentCurrencies, matchedRestCurrencies, showProgress ->
        SearchModelState(
            searchText,
            matchedRecentCurrencies,
            matchedRestCurrencies,
            showProgress
        )
    }

    init {
        retrieveCurrencies()
    }

    private fun retrieveCurrencies() {
        val listRecentCurrencies = DataSource.listRecentCurrencies
        val listRestCurrencies = DataSource.listRestCurrencies

        arrayListRecentCurrencies.addAll(listRecentCurrencies)
        arrayListRestCurrencies.addAll(listRestCurrencies)
        matchedRecentCurrencies.value = arrayListRecentCurrencies
        matchedRestCurrencies.value = arrayListRestCurrencies

    }

    fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
        if (changedSearchText.isEmpty()) {
            matchedRecentCurrencies.value = arrayListRecentCurrencies
            matchedRestCurrencies.value = arrayListRestCurrencies
            return
        }
        val recentCurrenciesFromSearch = arrayListRecentCurrencies.filter { x ->
            x.name.contains(changedSearchText, true)
        }
        val restCurrenciesFromSearch = arrayListRestCurrencies.filter { x ->
            x.name.contains(changedSearchText, true)
        }
        matchedRecentCurrencies.value = recentCurrenciesFromSearch
        matchedRestCurrencies.value = restCurrenciesFromSearch

    }

    fun onClearClick() {
        searchText.value = ""
        matchedRecentCurrencies.value = arrayListRecentCurrencies
        matchedRestCurrencies.value = arrayListRestCurrencies
    }
}