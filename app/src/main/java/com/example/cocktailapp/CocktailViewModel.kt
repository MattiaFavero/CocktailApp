package com.example.cocktailapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class CocktailViewModel : ViewModel() {

    private val api = CocktailApiService.create()

    var cocktails by mutableStateOf<List<Cocktail>>(emptyList())
        private set

    var query by mutableStateOf("")

    fun search() {
        viewModelScope.launch {
            try {
                val response = api.searchCocktails(query)
                cocktails = response.drinks ?: emptyList()
            } catch (e: Exception) {
                // handle error
                cocktails = emptyList()
            }
        }
    }
}
