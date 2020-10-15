package com.example.medled.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.medled.R
import kotlinx.android.synthetic.main.fragment_choose_avatar.*


class ChooseAvatarFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation(){
        changeAvatarBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        saveAvatarButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}