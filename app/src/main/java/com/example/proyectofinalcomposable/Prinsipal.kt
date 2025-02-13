package com.example.proyectofinalcomposable

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.NavController
import com.example.proyectofinalcomposable.retrofit.PokeApiService
import com.example.proyectofinalcomposable.retrofit.PokemonResponse
import com.example.proyectofinalcomposable.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Principal(navController: NavController) {
    val context = LocalContext.current
    var pokemon by remember { mutableStateOf<PokemonResponse?>(null) }
    val pokeApiService = RetrofitClient.instance.create(PokeApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var hasGeneratedPokemon by remember { mutableStateOf(false) }
    var stopReminders by remember { mutableStateOf(false) } // Nueva variable para detener los mensajes

    // Muestra un Toast al entrar en esta pantalla
    LaunchedEffect(Unit) {
        Toast.makeText(context, "Bienvenido a la Pokedex", Toast.LENGTH_SHORT).show()
    }

    // Hilo para mostrar un mensaje cada 5 segundos
    val handler = remember { Handler(Looper.getMainLooper()) }
    val reminderRunnable = remember {
        object : Runnable {
            override fun run() {
                if (!hasGeneratedPokemon && !stopReminders) {
                    Toast.makeText(context, "Recuerda generar un Pokémon aleatorio!", Toast.LENGTH_SHORT).show()
                    handler.postDelayed(this, 5000)
                }
            }
        }
    }

    // Iniciar los mensajes
    LaunchedEffect(Unit) {
        handler.postDelayed(reminderRunnable, 5000)
    }

    // fecha demo
    val currentDate = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // uso de la api
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            pokemon?.let {
                Image(
                    painter = rememberAsyncImagePainter(it.sprites.front_default),
                    contentDescription = "Imagen de ${it.name}",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 8.dp)
                )
                Text(text = "N° ${it.id}", fontSize = 20.sp)
                Text(text = it.name.replaceFirstChar { c -> c.uppercaseChar() }, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                coroutineScope.launch {
                    val randomId = (1..1025).random() // parámetro de elección de los pokemones por su id
                    pokemon = pokeApiService.getPokemon(randomId)
                    hasGeneratedPokemon = true // Detener los mensajes
                }
            }) {
                Text(text = "Cargar Pokémon Aleatorio")
            }
        }

        // numero demo
        Text(
            text = "Número",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            fontSize = 20.sp
        )

        // fecha demo
        Text(
            text = "Fecha: $currentDate",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            fontSize = 18.sp
        )

        // para cerrar secion
        Button(
            onClick = {
                stopReminders = true // Detiene los mensajes
                handler.removeCallbacks(reminderRunnable)
                navController.navigate("loguear")
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text(text = "Cerrar Sesión")
        }
    }
}