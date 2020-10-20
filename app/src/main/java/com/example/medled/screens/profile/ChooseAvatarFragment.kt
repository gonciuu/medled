package com.example.medled.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.medled.R
import kotlinx.android.synthetic.main.fragment_choose_avatar.*


class ChooseAvatarFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        for(i in 0 until listOfAvatarsImageViews.size){
            listOfAvatarsImageViews[i].setImageResource(if(false) listOfDoctorsImages[i] else listOfPatientsImages[i])
        }

    }
}