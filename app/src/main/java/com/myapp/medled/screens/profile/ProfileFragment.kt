package com.myapp.medled.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.myapp.medled.R
import com.myapp.medled.authentication.Authentication
import com.myapp.medled.view_models.CurrentUserViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private lateinit var  currentUserViewModel: CurrentUserViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUserViewModel = ViewModelProvider(requireActivity()).get(com.myapp.medled.view_models.CurrentUserViewModel::class.java)


        changeAvatarBox.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_chooseAvatarFragment)
        }

        logOutBox.setOnClickListener {
            Authentication().signOutFromFirebase(requireView())
        }

        setupUserInfo()
        boxesClick()

    }

    //-------------------------| Setup user info in profile fragment |---------------------------------
    private fun setupUserInfo(){

        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer { user->
            if(user!=null){
                currentUserName.text = user.name
                currentUserAvatar.setImageResource(user.avatar)
                currentUserBio.text = if(user.isDoctor) user.medicineBranch else user.bio

                //hide chang medicine branch option
                if(!user.isDoctor){
                    changeMedicineBranchDivider.visibility = View.GONE
                    changeMedicineBranchBox.visibility = View.GONE
                }
            }

        })
    }
    //==================================================================================================

    //-------------------------------------| Setup menu boxes clicks |------------------------------------------
    private fun boxesClick(){
        changeNameBox.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changeName)
        }
        changeBioBox.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changeBio)
        }

        changeMedicineBranchBox.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changeMedicineBranch)
        }

        changePasswordBox.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changePassword)
        }

        sourcesBox.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_sourcesFragment)
        }

    }
    //==========================================================================================================


}