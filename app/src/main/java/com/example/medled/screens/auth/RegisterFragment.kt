package com.example.medled.screens.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.medled.R
import com.example.medled.authentication.Authentication
import com.example.medled.helpers.Helpers
import com.example.medled.models.User
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*


class RegisterFragment : Fragment() {

    private lateinit var user: User


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //user object to save in database
        user = User( Calendar.getInstance().timeInMillis.toInt(),"Name","A doctor", R.drawable.doctor_avatar_1, true, "Cardiologist" )

        setupNavigation()
        registerButton.setOnClickListener { registerWithEmailAndPassword() }
        onEnterClicked()
        setUserTypeButtons()
        setupDoctorsTypesAutoCompleteTextView()
    }

    private fun setupNavigation() {
        registerBackButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    //------------------------| Register User With Email And Password |-----------------------------------
    private fun registerWithEmailAndPassword() {
        user.name = registerFullNameInput.text.toString()
        user.bio = if(user.isDoctor) "A Doctor" else "A patient"
        val authentication = Authentication()
        authentication.registerWithEmailAndPassword(registerEmailInput.text.toString(), registerPasswordInput.text.toString(),requireView(), user)
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


    //-------------------------| Patient/Doctor choose button |---------------------------
    private fun setUserTypeButtons(){
        doctorButton.setOnClickListener {
            user.isDoctor = true
            changeColors(doctorButton)
            doctorTypes.visibility = View.VISIBLE
        }
        patientButton.setOnClickListener {
            user.isDoctor = false
            changeColors(patientButton)
            doctorTypes.visibility = View.GONE
        }
    }
    //====================================================================================

    //---------------------------------| INVERSE BUTTON COLORS |-------------------------------------
    private fun changeColors(clickedButton: Button){
        arrayListOf(doctorButton,patientButton).forEach {
            it.backgroundTintList = ContextCompat.getColorStateList( requireContext(),R.color.bg_cyan)
            it .setTextColor(ContextCompat.getColor(requireContext(),R.color.darkGray))
        }
        clickedButton.backgroundTintList = ContextCompat.getColorStateList( requireContext(),R.color.colorPrimary)
        clickedButton .setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
    }
    //===============================================================================================

    //------------------------| Setup auto complete text view with doctors types |-------------------------
    private fun setupDoctorsTypesAutoCompleteTextView(){
        doctorTypes.setAdapter( ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,
            arrayListOf("Pediatrician","Neurologist","Family Doctor","Psychiatrist","Pulmonologist","Dermatologist","Cardiologist")))
        doctorTypes.inputType = 0
        doctorTypes.keyListener = null
        doctorTypes.setOnClickListener {
            doctorTypes.showDropDown()
        }

        doctorTypes.setOnItemClickListener { _, _, i, _ ->
            user.medicineBranch = doctorTypes.adapter.getItem(i).toString()
        }
    }
    //==========================================================================================================

}