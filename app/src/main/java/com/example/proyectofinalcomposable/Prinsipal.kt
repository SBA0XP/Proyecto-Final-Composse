package com.example.proyectofinalcomposable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Principal(navController: NavController) {
    Column {
        Text(text = "Pantalla Principal")
    }
}
