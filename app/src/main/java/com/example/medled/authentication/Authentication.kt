package com.example.medled.authentication

import android.app.AlertDialog
import android.view.View
import com.example.medled.databases.real_time_database.DatabaseError
import com.example.medled.databases.real_time_database.RealTimeDatabase
import com.example.medled.helpers.Helpers
import com.example.medled.models.User
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class Authentication: DatabaseError {

    //realtime database
    private val database = RealTimeDatabase()

    //google firebase auth
    private val auth = FirebaseAuth.getInstance()

    //helpers (show dialogs, snackbars etc)
    private val helpers: Helpers = Helpers()

    //----------------------| Register With email and password |----------------------------
    fun registerWithEmailAndPassword(email: String, password: String,view: View,user: User) {
        val dialog :AlertDialog = helpers.getLoadingDialog(view.context,"Registering")  //loading dialog
        try {
            dialog.show()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    //change user id to the current user uid in authentication
                    user.id = task.result!!.user.uid
                    //insert user to database
                    database.insertUserToDatabase(user,view,this)
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

    //------------------| Sign out |-----------------------
    fun signOutFromFirebase(view: View) = try {
        helpers.showSnackBar("Log outed",view)
        auth.signOut()
    }catch (ex:Exception){
        helpers.showSnackBar(ex.message.toString(),view)
    }

    //=====================================================


    //----------------| function which handled database error |----------------------
    override fun errorHandled(errorMessage:String,view: View) {
        helpers.showSnackBar(errorMessage,view)
        if(auth.currentUser!=null)
            auth.currentUser!!.delete()
    }
    //================================================================================

    //---------------------| Get current user id |----------------------
    fun getCurrentUserId():String?{
        return try{
            auth.currentUser!!.uid
        }catch (ex:Exception){
            null
        }
    }
    //===================================================================
}