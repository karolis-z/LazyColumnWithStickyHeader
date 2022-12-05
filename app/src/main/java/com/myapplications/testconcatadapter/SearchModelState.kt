package com.myapplications.testconcatadapter

data class SearchModelState(
    val searchText: String = "",
    val recentCurrencies: List<Currency> = arrayListOf(),
    val restCurrencies: List<Currency> = arrayListOf(),
    val showProgressBar: Boolean = false
) {

    companion object {
        val empty = SearchModelState()
    }

}
