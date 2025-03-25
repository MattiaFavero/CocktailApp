// MainActivity.kt
package com.example.cocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cocktailapp.Cocktail
import com.example.cocktailapp.CocktailViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<CocktailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(viewModel, navController)
                    }
                    composable("detail/{cocktailId}",
                        arguments = listOf(navArgument("cocktailId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val cocktailId = backStackEntry.arguments?.getString("cocktailId")
                        val cocktail = viewModel.cocktails.find { it.idDrink == cocktailId }
                        cocktail?.let { DetailScreen(it) }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: CocktailViewModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = viewModel.query,
            onValueChange = { viewModel.query = it },
            label = { Text("Search Cocktail") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.search() },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Search")
        }

        viewModel.cocktails.forEach { cocktail ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("detail/${cocktail.idDrink}") }
                    .padding(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(cocktail.strDrinkThumb),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = cocktail.strDrink, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Ingredients: " + cocktail.getIngredients().joinToString(", "),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreen(cocktail: Cocktail) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberAsyncImagePainter(cocktail.strDrinkThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = cocktail.strDrink, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Ingredients:", style = MaterialTheme.typography.titleMedium)
        cocktail.getIngredients().forEach { ingredient ->
            Text("- $ingredient", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Instructions:", style = MaterialTheme.typography.titleMedium)
        Text(cocktail.strInstructions ?: "No instructions available.")
    }
}
