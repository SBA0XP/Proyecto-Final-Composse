package com.example.proyectofinalcomposable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Loguear(navController: NavController) {
    val userInput = remember { androidx.compose.runtime.mutableStateOf("") }

    // Usamos Column con Modifier.fillMaxSize() para llenar toda la pantalla.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Espaciado de 16dp en todos los lados
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center // Centrado vertical
    ) {
        // Título
        Text(text = "Pantalla de Logueo")

        // Campo de texto para "si" o "no".
        OutlinedTextField(
            value = userInput.value,
            onValueChange = { userInput.value = it },
            label = { Text("Escribe 'si' o 'no'") },
            modifier = Modifier.fillMaxWidth() // Hace que el campo ocupe todo el ancho
        )

        // Espacio entre el texto y el boton.
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

        // Botón para confirmar y navegar.
        Button(onClick = {
            // la navegacion depende de el texto que introduscamos.
            if (userInput.value.lowercase() == "si") {
                navController.navigate("registrar")
            } else if (userInput.value.lowercase() == "no") {
                navController.navigate("principal")
            }
        }) {
            Text(text = "Confirmar")
        }
    }
}

