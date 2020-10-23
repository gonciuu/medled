package com.myapp.medled.screens.auth

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myapp.medled.R
import com.myapp.medled.authentication.Authentication
import com.myapp.medled.helpers.Helpers
import com.myapp.medled.models.User
import com.myapp.medled.screens.auth.time_picker_dialog.RegisterTimePickerDialog
import com.myapp.medled.view_models.RegisterTimeViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*


class RegisterFragment : Fragment() {

    private lateinit var user: User
    private lateinit var registerTimeViewModel: RegisterTimeViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerTimeViewModel = ViewModelProvider(requireActivity()).get(RegisterTimeViewModel::class.java)

        //user object to save in database
        user = User(System.currentTimeMillis().toString(),"Name","A doctor", R.drawable.doctor_avatar_1, true, "Cardiologist" , System.currentTimeMillis(), System.currentTimeMillis(),0.0f)

        setupNavigation()
        registerButton.setOnClickListener { registerWithEmailAndPassword() }
        onEnterClicked()
        setUserTypeButtons()
        setupDoctorsTypesAutoCompleteTextView()
        setupStartAndEndWorkTime()
    }

    private fun setupNavigation() {
        registerBackButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    //------------------------| Register User With Email And Password |-----------------------------------
    private fun registerWithEmailAndPassword() {
        //set user info
        user.name = registerFullNameInput.text.toString()
        user.bio = if(user.isDoctor) "A Doctor" else "A patient"
        user.avatar =  if(user.isDoctor) R.drawable.doctor_avatar_1 else R.drawable.user_avatar_1


        //check if the start time is lower than end time
        if(user.startTime!! > user.endTime!!){
            Log.d("TIME",user.startTime.toString())
            Log.d("TIME",user.endTime.toString())
            Helpers().showSnackBar("Your work start time is lower than your end time!", requireView())
        }else{
            val authentication = Authentication()
            authentication.registerWithEmailAndPassword(registerEmailInput.text.toString(), registerPasswordInput.text.toString(),requireView(), user)
        }

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
        val listOfDoctorViews = arrayListOf<View>(doctorTypes, startTimeInput, endTimeInput,startTimeInput2,timeIntervalText)
        doctorButton.setOnClickListener {
            user.isDoctor = true
            changeColors(doctorButton)
            listOfDoctorViews.forEach { it.visibility = View.VISIBLE}
        }
        patientButton.setOnClickListener {
            user.isDoctor = false
            changeColors(patientButton)
            listOfDoctorViews.forEach { it.visibility = View.GONE}
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

        //change medicine branch of user
        doctorTypes.setOnItemClickListener { _, _, i, _ ->
            user.medicineBranch = doctorTypes.adapter.getItem(i).toString()
        }
    }
    //==========================================================================================================


    //--------------------------| Setup start/end time pickers |-------------------------------
    private fun setupStartAndEndWorkTime(){
        val calendar = Calendar.getInstance()

        //-----------------------------| get work start time and setup it into the textview |---------------------------
        registerTimeViewModel.getStartTime().observe(viewLifecycleOwner, androidx.lifecycle.Observer { startTime->
            calendar.timeInMillis = startTime
            startTimeInput.text = DateFormat.format("HH:mm", calendar).toString()
            user.startTime = startTime
        })
        //==============================================================================================================

        //-----------------------------| get work end time and setup it into the textview |---------------------------
        registerTimeViewModel.getEndTime().observe(viewLifecycleOwner, androidx.lifecycle.Observer { endTime->
            calendar.timeInMillis = endTime
            endTimeInput.text = DateFormat.format("HH:mm", calendar).toString()
            user.endTime = endTime
        })
        //============================================================================================================

        setStartTimeValues()


        //show time picker to set start time
        startTimeInput.setOnClickListener {
            val dialog = RegisterTimePickerDialog(true)
            dialog.show(requireActivity().supportFragmentManager,"start_time_dialog")
        }

        //show time picker to set end time
        endTimeInput.setOnClickListener {
            val dialog = RegisterTimePickerDialog(false)
            dialog.show(requireActivity().supportFragmentManager,"end_time_dialog")
        }
    }
    //=========================================================================================

    //--------------| Set doctor times start values |------------------
    private fun setStartTimeValues(){
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,8)
        c.set(Calendar.MINUTE,0)
        registerTimeViewModel.setStartTime(c.timeInMillis)
        c.set(Calendar.HOUR_OF_DAY,12)
        c.set(Calendar.MINUTE,0)
        registerTimeViewModel.setEndTime(c.timeInMillis)
    }
    //===================================================================

}