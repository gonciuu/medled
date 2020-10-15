package com.example.medled.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.medled.R
import com.example.medled.authentication.Authentication
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        registerButton.setOnClickListener { registerWithEmailAndPassword() }

    }

    private fun setupNavigation() {
        registerBackButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    //------------------------| Register User With Email And Password |-----------------------------------
    private fun registerWithEmailAndPassword() {
        val authentication = Authentication()
        authentication.registerWithEmailAndPassword(registerEmailInput.text.toString(), registerPasswordInput.text.toString())
        
        { Toast.makeText(requireContext(), "SUKCES", Toast.LENGTH_SHORT).show() }
    }
    //=====================================================================================================


}