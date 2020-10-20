package com.example.medled.screens.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medled.R
import com.example.medled.view_models.ChooseDoctorViewModel
import kotlinx.android.synthetic.main.fragment_reserve_message.*

class ReserveMessageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reserve_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        setupDoctorInfo()

    }

    private fun setupNavigation() =  reserveMessageBackButton.setOnClickListener { requireActivity().onBackPressed() }

    //--------------------| Set doctors info data in layout |---------------------------
    private fun setupDoctorInfo(){
        val chooseDoctorViewModel:ChooseDoctorViewModel = ViewModelProvider(requireActivity()).get(ChooseDoctorViewModel::class.java)

        chooseDoctorViewModel.getDoctor().observe(viewLifecycleOwner, Observer { doctor->
            chooseDoctorAvatar.setImageResource(doctor.avatar)
            chooseDoctorName.text = doctor.name
            chooseDoctorMedicineBranch.text = doctor.medicineBranch
            chooseDoctorBio.text = doctor.bio
            chooseDoctorStarCount.text = doctor.starCount.toString()
        })
    }
    //===================================================================================




}