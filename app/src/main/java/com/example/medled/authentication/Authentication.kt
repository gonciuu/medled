package com.example.medled.authentication

import android.app.AlertDialog
import android.view.View
import com.example.medled.helpers.Helpers
import com.google.firebase.auth.FirebaseAuth

class Authentication {
    private val auth = FirebaseAuth.getInstance()
    private val helpers: Helpers = Helpers()

    fun registerWithEmailAndPassword(email: String, password: String,view: View, complete : () -> Unit) {
        val dialog :AlertDialog = helpers.getDialog(view.context,"Registering")
        dialog.show()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){

                complete()
            }else{

            }
            dialog.dismiss()
        }
    }
}