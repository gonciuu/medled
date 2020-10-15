package com.example.medled.authentication

import com.google.firebase.auth.FirebaseAuth

class Authentication {
    private val auth = FirebaseAuth.getInstance()

    fun registerWithEmailAndPassword(email: String, password: String, complete : () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                complete()
            }else{

            }
        }
    }
}