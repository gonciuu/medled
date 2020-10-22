package com.example.medled.screens.profile.user_config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.medled.R
import com.example.medled.authentication.Authentication
import com.example.medled.helpers.Helpers
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