package com.example.proyectofinalcomposable

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)?.let {
            Log.d("FirebaseInit", "Firebase se inicializó correctamente")
        } ?: Log.e("FirebaseInit", "Error al inicializar Firebase")

        setContent {
            ProyectoFinalComposableTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "loguear") {
                    composable("loguear") { Loguear(navController) }
                    composable("registrar") { Registrar(navController) }
                    composable("principal") {Principal(navController)
                    }
                }
            }
        }
    }
}