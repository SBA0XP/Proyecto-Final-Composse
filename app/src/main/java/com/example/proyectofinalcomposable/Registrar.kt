package com.example.proyectofinalcomposable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Registrar(navController: NavController) {
    Column {
        Text(text = "Pantalla de Registro")

        // Usamos el botón para ir hacia la pantalla de Loguear
        Button(onClick = {
            navController.navigate("loguear")
        }) {
            Text(text = "Regresar a Loguear")
        }
    }
}

