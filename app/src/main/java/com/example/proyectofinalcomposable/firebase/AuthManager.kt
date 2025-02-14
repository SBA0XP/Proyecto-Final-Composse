package com.example.proyectofinalcomposable.firebase

import com.google.firebase.auth.FirebaseAuth

class AuthManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestoreManager = FirestoreManager()

    fun registerUser(email: String, password: String, nombre: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        firestoreManager.saveUser(userId, email, nombre) { success ->
                            if (success) {
                                callback(true, userId)
                            } else {
                                callback(false, "Error al guardar en Firestore")
                            }
                        }
                    } else {
                        callback(false, "Error al obtener el UID del usuario")
                    }
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        firestoreManager.updateUserAccess(userId)
                    }
                    callback(true, userId)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    /*fun logout() {
        auth.signOut()
    }*/

   /* fun getCurrentUser(): String? {
        return auth.currentUser?.uid
    }*/
}