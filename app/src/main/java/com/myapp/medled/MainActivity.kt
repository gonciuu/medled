package com.myapp.medled

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.myapp.medled.authentication.Authentication
import com.myapp.medled.view_models.CurrentUserViewModel
import com.myapp.medled.view_models.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_chat.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_nav.setupWithNavController(findNavController(R.id.nav_host_fragment))
        handleUserAuthStateChanged()
        setBottomNavigationVisibility()
    }

    //---------------| Handle user log in\log out |------------------
    private fun handleUserAuthStateChanged() {
        val authentication: Authentication = Authentication()
        authentication.handleAuthStateChanged({ stateLogIn() }, { stateLogOut() })
    }
    //===============================================================

    //log in to app action
    private fun stateLogIn() {
        val navController = findNavController(R.id.nav_host_fragment)
        if(navController.currentDestination!!.id == R.id.welcomeAuthFragment || navController.currentDestination!!.id == R.id.loginFragment || navController.currentDestination!!.id == R.id.registerFragment)
            navController.navigate(R.id.medicinesFragment, null, getNavOptions())
    }

    //log out from app action
    private fun stateLogOut() {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination!!.id != R.id.welcomeAuthFragment)
            navController.navigate(R.id.welcomeAuthFragment, null, getNavOptions())

        val currentUserViewModel: CurrentUserViewModel = ViewModelProvider(this).get(
            CurrentUserViewModel::class.java)
        currentUserViewModel.setUser(null)
    }


    //----------------------| Add animations between navigate to another fragment |-----------------------
    private fun getNavOptions(): NavOptions? {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopUpTo(R.id.medled_nav_graph, true)
            .build()
    }
    //=====================================================================================================


    //-----------------------------| Show or hide bottom bar based on current destination in navigation component |-------------------------------

    private fun setBottomNavigationVisibility() {
        val mainViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainViewModel.bottomNavVisibility.observe(this, Observer {
            bottom_nav.visibility = it
        })
            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.profileFragment || destination.id == R.id.medicinesFragment || destination.id == R.id.allDoctorsFragment)
                mainViewModel.showBottomNavigation()
            else mainViewModel.hideBottomNavigation()
        }
    }

    //=============================================================================================================================================


    //-----------------| handle back button press in the chat fragment |-----------------------------
    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination!!.id == R.id.chatFragment){
            exitChatButton.performClick()
        }else{
            super.onBackPressed()
        }
    }
    //===============================================================================================
}