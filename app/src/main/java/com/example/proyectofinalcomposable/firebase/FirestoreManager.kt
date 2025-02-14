package com.example.proyectofinalcomposable.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreManager {
    private val db = FirebaseFirestore.getInstance()

    fun saveUser(userId: String, email: String, nombre: String, callback: (Boolean) -> Unit) {
        val user = hashMapOf(
            "correo" to email,
            "nombre" to nombre,
            "numero_accesos" to 0,
            "ultimo_acceso" to Timestamp.now()
        )

        db.collection("Usuarios").document(userId)
            .set(user)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { e ->
                println("Error al guardar usuario: ${e.message}")
                callback(false)
            }
    }

    fun updateUserAccess(userId: String) {
        val userRef = db.collection("Usuarios").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val currentAccesses = document.getLong("numero_accesos") ?: 0

                userRef.update(
                    "numero_accesos", currentAccesses + 1,
                    "ultimo_acceso", Timestamp.now()
                )
            }
        }.addOnFailureListener {
            println("Error al actualizar acceso del usuario: ${it.message}")
        }
    }
}
