package com.example.medled.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.medled.R
import com.example.medled.authentication.Authentication
import com.example.medled.view_models.CurrentUserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeAvatarBox.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_chooseAvatarFragment)
        }

        logOutBox.setOnClickListener {
            Authentication().signOutFromFirebase(requireView())
        }

        setupUserInfo()

    }

    //-------------------------| Setup user info in profile fragment |---------------------------------
    private fun setupUserInfo(){
        val currentUserViewModel: CurrentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)
        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer { user->
            if(user!=null){
                currentUserName.text = user.name
                currentUserAvatar.setImageResource(user.avatar)
                currentUserBio.text = if(user.isDoctor) user.medicineBranch else user.bio
            }

        })
    }
    //==================================================================================================
}