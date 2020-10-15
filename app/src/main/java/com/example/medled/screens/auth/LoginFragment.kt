package com.example.medled.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.medled.R
import com.example.medled.authentication.Authentication
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        signInButton.setOnClickListener { loginWithEmailAndPassword() }
    }

    private fun setupNavigation() {
        loginBackButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun loginWithEmailAndPassword() {
        val authentication: Authentication = Authentication()
        authentication.loginWithEmailAndPassword(
            loginEmailInput.text.toString(),
            loginPasswordInput.text.toString(),
            requireView()
        ) { Toast.makeText(requireContext(), "SUKCES", Toast.LENGTH_SHORT).show() }
    }

}