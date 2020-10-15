package com.example.medled

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.medled.authentication.Authentication

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleUserAuthStateChanged()
    }

    //---------------| Handle user log in\log out |------------------
    private fun handleUserAuthStateChanged(){
        val authentication:Authentication = Authentication()
        authentication.handleAuthStateChanged({stateLogIn()},{stateLogOut()})
    }
    //===============================================================

    //log in to app action
    private fun stateLogIn()  = findNavController(R.id.nav_host_fragment).navigate(R.id.medicinesFragment,null,getNavOptions())

    //log out from app action
    private fun stateLogOut() {
        val navController =  findNavController(R.id.nav_host_fragment)
        if(navController.currentDestination!!.id!=R.id.welcomeAuthFragment)
            navController.navigate(R.id.welcomeFragment,null,getNavOptions())
    }



    //----------------------| Add animations between navigate to another fragment |-----------------------
    private fun getNavOptions(): NavOptions? {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopUpTo(R.id.medled_nav_graph,true)
            .build()
    }
    //=====================================================================================================
}