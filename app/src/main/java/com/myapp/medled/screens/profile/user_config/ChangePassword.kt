package com.myapp.medled.screens.profile.user_config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myapp.medled.R
import com.myapp.medled.authentication.Authentication
import com.myapp.medled.helpers.Helpers
import kotlinx.android.synthetic.main.fragment_change_password.*


class ChangePassword : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        saveNewPasswordButton.setOnClickListener {
            changePassword()
        }

        //soft keyboard enter click
        Helpers().keyboardEnterButtonClick(newPasswordInput){saveNewPasswordButton.performClick()}
    }

    private fun setupNavigation() = changePasswordBackButton.setOnClickListener { requireActivity().onBackPressed() }

    //------------------------| Change the password |--------------------------
    private fun changePassword(){
        Authentication().changePassword(oldPasswordInput.text.toString(),newPasswordInput.text.toString(),requireView()){
            requireActivity().onBackPressed()
        }
    }
    //=========================================================================

}