package com.example.medled.screens.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.medled.R
import com.example.medled.authentication.Authentication
import com.example.medled.helpers.Helpers
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        registerButton.setOnClickListener { registerWithEmailAndPassword() }
        onEnterClicked()
    }

    private fun setupNavigation() {
        registerBackButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    //------------------------| Register User With Email And Password |-----------------------------------
    private fun registerWithEmailAndPassword() {
        val authentication = Authentication()
        authentication.registerWithEmailAndPassword(registerEmailInput.text.toString(), registerPasswordInput.text.toString(),requireView())

        { Toast.makeText(requireContext(), "SUKCES", Toast.LENGTH_SHORT).show() }
    }
    //=====================================================================================================

    //-----------------| unfocus cursor and close keyboard by enter click on keyboard when put the password |------------------
    private fun onEnterClicked(){
        Helpers().keyboardEnterButtonClick(registerPasswordInput){
            registerPasswordInput.clearFocus()            
            val imm: InputMethodManager? =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }
    //=====================================================================================================

}