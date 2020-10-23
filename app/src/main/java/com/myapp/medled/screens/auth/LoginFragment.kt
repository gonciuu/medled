package com.myapp.medled.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myapp.medled.R
import com.myapp.medled.authentication.Authentication
import com.myapp.medled.helpers.Helpers
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        signInButton.setOnClickListener { loginWithEmailAndPassword() }
        onEnterClicked()
    }

    private fun setupNavigation() {
        loginBackButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    //--------| Login to app With Email and Password |------------
    private fun loginWithEmailAndPassword() {
        val authentication: Authentication = Authentication()
        authentication.loginWithEmailAndPassword(
            loginEmailInput.text.toString(),
            loginPasswordInput.text.toString(),
            requireView()
        )
    }
    //=============================================================


    //-----------------| Log in to app by enter click on keyboard when put the password |------------------
    private fun onEnterClicked(){
        Helpers().keyboardEnterButtonClick(loginPasswordInput){
            signInButton.performClick()
        }
    }
    //=====================================================================================================

}