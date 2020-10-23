package com.myapp.medled.screens.profile.user_config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myapp.medled.R
import com.myapp.medled.databases.real_time_database.DatabaseError
import com.myapp.medled.databases.real_time_database.RealTimeDatabase
import com.myapp.medled.helpers.Helpers
import com.myapp.medled.models.User
import com.myapp.medled.view_models.CurrentUserViewModel
import kotlinx.android.synthetic.main.fragment_choose_avatar.*
import java.lang.Exception


class ChooseAvatarFragment : Fragment(),DatabaseError {

    private lateinit var currentUserViewModel: CurrentUserViewModel
    private var chooseAvatarResource : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)
        setupNavigation()
        setupImages()
    }

    private fun setupNavigation() {
        changeAvatarBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        saveAvatarButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    private fun setupImages() {

        //all avatars image views
        val listOfAvatarsImageViews = arrayListOf<ImageView>(
            avatar1, avatar2, avatar3, avatar4, avatar5, avatar6
        )

        //list of doctors avatars
        val listOfDoctorsImages = arrayListOf<Int>(
            R.drawable.doctor_avatar_1,
            R.drawable.doctor_avatar_2,
            R.drawable.doctor_avatar_3,
            R.drawable.doctor_avatar_4,
            R.drawable.doctor_avatar_5,
            R.drawable.doctor_avatar_6
        )

        //list of patients avatars
        val listOfPatientsImages = arrayListOf<Int>(
            R.drawable.user_avatar_1,
            R.drawable.user_avatar_2,
            R.drawable.user_avatar_3,
            R.drawable.user_avatar_4,
            R.drawable.user_avatar_5,
            R.drawable.user_avatar_6
        )


        //-------------------| get currentUser |--------------------
        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer { user->
            try{
                chooseAvatar.setImageResource(user!!.avatar)
                for(i in 0 until listOfAvatarsImageViews.size){
                    listOfAvatarsImageViews[i].setImageResource(if(user.isDoctor) listOfDoctorsImages[i] else listOfPatientsImages[i])

                    listOfAvatarsImageViews[i].setOnClickListener {
                        chooseAvatar.setImageResource(if(user.isDoctor) listOfDoctorsImages[i] else listOfPatientsImages[i])
                        chooseAvatarResource = if(user.isDoctor) listOfDoctorsImages[i] else listOfPatientsImages[i]
                    }
                }
                saveAvatarButton.setOnClickListener { saveAvatar(user) }
            }catch (ex:Exception){}

        })
        //============================================================
    }

    //-----| save avatar to database|-------
    private fun saveAvatar(user:User){
        user.avatar = chooseAvatarResource
        val db:RealTimeDatabase = RealTimeDatabase()
        db.insertUserToDatabase(user,requireView(),this)
        requireActivity().onBackPressed()
    }
    //======================================

    override fun errorHandled(errorMessage: String, view: View) {
       Helpers().showSnackBar(errorMessage,requireView())
    }
}