package com.example.medled.authentication

import android.app.AlertDialog
import android.view.View
import com.example.medled.helpers.Helpers
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class Authentication {
    private val auth = FirebaseAuth.getInstance()
    private val helpers: Helpers = Helpers()

    //----------------------| Register With email and password |----------------------------
    fun registerWithEmailAndPassword(email: String, password: String,view: View) {
        val dialog :AlertDialog = helpers.getLoadingDialog(view.context,"Registering")  //loading dialog
        try {
            dialog.show()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    helpers.showSnackBar("Successfully registering",view)
                }else{
                    //catch network exception etc
                    helpers.showSnackBar(task.exception!!.message.toString(),view)
                }
                dialog.dismiss()
            }
        }catch (ex:Exception){
            dialog.dismiss()
            helpers.showSnackBar(ex.message.toString(),view)
        }
    }
    //============================================================================================

    //----------------------| Login With email and password |----------------------------
    fun loginWithEmailAndPassword(email: String, password: String,view: View) {
        val dialog :AlertDialog = helpers.getLoadingDialog(view.context,"Logging")  //loading dialog
        try {
            dialog.show()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    helpers.showSnackBar("Successfully log in",view)
                }else{
                    //catch network exception etc
                    helpers.showSnackBar(task.exception!!.message.toString(),view)
                }
                dialog.dismiss()
            }
        }catch (ex:Exception){
            dialog.dismiss()
            helpers.showSnackBar(ex.message.toString(),view)
        }
    }
    //============================================================================================

    //-----------------------| Handle user already log in or on log out |----------------------------
    fun handleAuthStateChanged(actionLogIn: () -> Unit,actionLogOut: () -> Unit){
        auth.addAuthStateListener {firebaseAuth ->
            if(firebaseAuth.currentUser == null) actionLogOut()
            else actionLogIn()
        }
    }
    //===============================================================================================

    fun removeListener() {
        auth.removeAuthStateListener{}
    }


    //------------------| Sign out |-----------------------
    fun signOutFromFirebase(view: View) = try {
        helpers.showSnackBar("Log outed",view)
        auth.signOut()
    }catch (ex:Exception){
        helpers.showSnackBar(ex.message.toString(),view)
    }
    //=====================================================


}