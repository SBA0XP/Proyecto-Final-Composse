package com.example.proyectofinalcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalcomposable.ui.theme.ProyectoFinalComposableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalComposableTheme {
                val navController = rememberNavController()

                // confuguracion del nav controller
                NavHost(navController = navController, startDestination = "loguear") {
                    composable("loguear") { Loguear(navController) }
                    composable("registrar") { Registrar(navController) }
                    composable("principal") { Principal(navController) }
                }
            }
        }
    }
}

