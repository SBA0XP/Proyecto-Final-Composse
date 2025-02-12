package com.example.proyectofinalcomposable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectofinalcomposable.retrofit.Pokemon
import com.example.proyectofinalcomposable.retrofit.RetrofitClient
import com.example.proyectofinalcomposable.retrofit.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Principal(navController: NavController) {
    var pokemon by remember { mutableStateOf<Pokemon?>(null) }
    var pokemonName by remember { mutableStateOf("pikachu") } // Nombre por defecto

    LaunchedEffect(pokemonName) {
        val call = RetrofitClient.apiService.getPokemon(pokemonName)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    pokemon = response.body()
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pantalla Principal", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        pokemon?.let {
            Text(text = "Nombre: ${it.name}")

            val painter: Painter = rememberAsyncImagePainter(it.sprites.front_default)
            Image(painter = painter, contentDescription = "Imagen de ${it.name}", modifier = Modifier.size(100.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            pokemonName = "bulbasaur" // Cambia el Pokémon
        }) {
            Text(text = "Cargar Bulbasaur")
        }
    }
}
