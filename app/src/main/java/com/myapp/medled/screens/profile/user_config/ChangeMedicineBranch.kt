package com.myapp.medled.screens.profile.user_config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myapp.medled.R
import com.myapp.medled.databases.real_time_database.DatabaseError
import com.myapp.medled.databases.real_time_database.RealTimeDatabase
import com.myapp.medled.helpers.Helpers
import com.myapp.medled.view_models.CurrentUserViewModel
import kotlinx.android.synthetic.main.fragment_change_medicine_branch.*


class ChangeMedicineBranch : Fragment(),DatabaseError {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_medicine_branch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupDoctorsTypesAutoCompleteTextView()
    }

    //setup Navigation
    private fun setupNavigation() = changeMedicineBranchBackButton.setOnClickListener { requireActivity().onBackPressed() }

    //------------------------| Setup auto complete text view with doctors types |-------------------------
    private fun setupDoctorsTypesAutoCompleteTextView(){
        val currentUserViewModel : CurrentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)

        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer {user->
            newMedicineBranchACTV.setText(user!!.medicineBranch,false)

            newMedicineBranchACTV.setAdapter( ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,
                arrayListOf("Pediatrician","Neurologist","Family Doctor","Psychiatrist","Pulmonologist","Dermatologist","Cardiologist"))
            )
            newMedicineBranchACTV.inputType = 0
            newMedicineBranchACTV.keyListener = null
            newMedicineBranchACTV.setOnClickListener {
                newMedicineBranchACTV.showDropDown()
            }

            //change medicine branch of user
            newMedicineBranchACTV.setOnItemClickListener { _, _, i, _ ->
                user.medicineBranch = newMedicineBranchACTV.adapter.getItem(i).toString()
            }

            //save new user branch into database
            saveNewMedicineBranchButton.setOnClickListener {
                RealTimeDatabase().insertUserToDatabase(user,requireView(),this)
                requireActivity().onBackPressed()
            }

        })
    }


    //==========================================================================================================

    //handle error
    override fun errorHandled(errorMessage: String, view: View) {
        Helpers().showSnackBar(errorMessage,requireView())
    }
}