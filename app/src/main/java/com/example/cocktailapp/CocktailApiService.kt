package com.example.cocktailapp

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CocktailApiService {
    @GET("search.php")
    suspend fun searchCocktails(@Query("s") query: String): CocktailResponse

    companion object {
        fun create(): CocktailApiService {
            return Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CocktailApiService::class.java)
        }
    }
}

data class CocktailResponse(val drinks: List<Cocktail>?)

data class Cocktail(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String?,
    val strInstructions: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?
) {
    // Function to dynamically retrieve all non-null ingredients
    fun getIngredients(): List<String> {
        return listOfNotNull(
            strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
            strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
            strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15
        )
    }
}

