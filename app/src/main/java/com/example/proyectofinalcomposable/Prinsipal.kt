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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Principal(navController: NavController) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    var pokemon by remember { mutableStateOf<PokemonResponse?>(null) }
    val pokeApiService = RetrofitClient.instance.create(PokeApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var hasGeneratedPokemon by remember { mutableStateOf(false) }
    var stopReminders by remember { mutableStateOf(false) }

    // Mostrar mensaje de bienvenida
    LaunchedEffect(Unit) {
        Toast.makeText(context, "Bienvenido a la Pokedex", Toast.LENGTH_SHORT).show()
    }

    // Hilo para mensajes concurrentes
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Contenido principal
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
                    val randomId = (1..1025).random()
                    pokemon = pokeApiService.getPokemon(randomId)
                    hasGeneratedPokemon = true
                }
            }) {
                Text(text = "Cargar Pokémon Aleatorio")
            }
        }

        // Botón para cerrar sesión
        Button(
            onClick = {
                stopReminders = true
                handler.removeCallbacks(reminderRunnable)
                navController.navigate("loguear")
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text(text = "Cerrar Sesión")
        }

        // Boton para cerrar la App
        Button(
            onClick = {
                stopReminders = true
                handler.removeCallbacks(reminderRunnable)
                // Cerrar la aplicación
                android.os.Process.killProcess(android.os.Process.myPid())
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Cerrar Aplicación")
        }
    }
}
