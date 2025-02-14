package com.example.proyectofinalcomposable

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinalcomposable.firebase.AuthManager

@Composable
fun Registrar(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authManager = AuthManager()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pantalla de Registro", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Correo Electrónico")
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Ingresa tu correo") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Nombre")
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Ingresa tu nombre") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (email.isNotEmpty() && nombre.isNotEmpty()) {
                authManager.registerUser(email, "123456", nombre) { success, message ->
                    if (success) {
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        navController.navigate("loguear") {
                            popUpTo("registrar") { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Confirmar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("loguear")
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Volver a Loguear")
        }
    }
}
