package com.myapp.medled.screens.profile.user_config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myapp.medled.R
import com.myapp.medled.databases.real_time_database.DatabaseError
import com.myapp.medled.databases.real_time_database.RealTimeDatabase
import com.myapp.medled.helpers.Helpers
import com.myapp.medled.view_models.CurrentUserViewModel
import kotlinx.android.synthetic.main.fragment_change_bio.*


class ChangeBio : Fragment() ,DatabaseError{
    private lateinit var currentUserViewModel: CurrentUserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_bio, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)

        setupNavigation()
        saveNewBio()
    }

    //setup nav
    private fun setupNavigation() =
        changeBioBackButton.setOnClickListener { requireActivity().onBackPressed() }


    //-----------------------------| Save new user bio in database |---------------------------------
    private fun saveNewBio() {
        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            changeBioInput.setText(user!!.bio)
            saveNewBioButton.setOnClickListener {
                user.bio = changeBioInput.text.toString()
                val db: RealTimeDatabase = RealTimeDatabase()
                db.insertUserToDatabase(user, requireView(), this)
                requireActivity().onBackPressed()
            }
            //soft keyboard enter click
            Helpers().keyboardEnterButtonClick(changeBioInput) { saveNewBioButton.performClick() }
        })
    }
    //===============================================================================================

    //handle eventual error
    override fun errorHandled(errorMessage: String, view: View) {
        Helpers().showSnackBar(errorMessage, requireView())
    }
}
