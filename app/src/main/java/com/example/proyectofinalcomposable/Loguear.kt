package com.example.proyectofinalcomposable

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

@Composable
fun Loguear(navController: NavController) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Pantalla de Logueo")

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Introduce tu correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (email.isNotEmpty()) {
                val emailLower = email.trim().lowercase() // Convercion a minúsculas y eliminamos espacios

                db.collection("Usuarios")
                    .whereEqualTo("correo", emailLower)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val userDoc = documents.documents[0]
                            val userId = userDoc.id

                            // Actualizar el nº de accesos
                            val numAccesses = userDoc.getLong("numero_accesos") ?: 0
                            val newAccessCount = numAccesses + 1
                            val currentTimestamp = Date()

                            db.collection("Usuarios").document(userId)
                                .update(
                                    "ultimo_acceso", currentTimestamp,
                                    "numero_accesos", newAccessCount
                                )
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Usuario encontrado y actualizado", Toast.LENGTH_SHORT).show()
                                    navController.navigate("principal")
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Error al actualizar datos", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(context, "Usuario no encontrado, regístrate", Toast.LENGTH_SHORT).show()
                            navController.navigate("registrar")
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al verificar el usuario", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Por favor ingresa un correo", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Confirmar")
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Boton para navegar a registrar
        Button(onClick = {
            navController.navigate("Registrar")
        }) {
            Text(text = "Registrarse")
        }
    }
}
