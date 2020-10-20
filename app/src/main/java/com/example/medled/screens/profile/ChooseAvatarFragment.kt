package com.example.medled.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medled.R
import com.example.medled.view_models.CurrentUserViewModel
import kotlinx.android.synthetic.main.fragment_choose_avatar.*


class ChooseAvatarFragment : Fragment() {

    private lateinit var currentUserViewModel: CurrentUserViewModel

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
        val listOfAvatarsImageViews = arrayListOf<ImageView>(
            avatar1, avatar2, avatar3, avatar4, avatar5, avatar6
        )

        val listOfDoctorsImages = arrayListOf<Int>(
            R.drawable.doctor_avatar_1,
            R.drawable.doctor_avatar_2,
            R.drawable.doctor_avatar_3,
            R.drawable.doctor_avatar_4,
            R.drawable.doctor_avatar_5,
            R.drawable.doctor_avatar_6
        )

        val listOfPatientsImages = arrayListOf<Int>(
            R.drawable.user_avatar_1,
            R.drawable.user_avatar_2,
            R.drawable.user_avatar_3,
            R.drawable.user_avatar_4,
            R.drawable.user_avatar_5,
            R.drawable.user_avatar_6
        )


        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer { user->
            for(i in 0 until listOfAvatarsImageViews.size){
                listOfAvatarsImageViews[i].setImageResource(if(user!!.isDoctor) listOfDoctorsImages[i] else listOfPatientsImages[i])
            }
        })

    }
}