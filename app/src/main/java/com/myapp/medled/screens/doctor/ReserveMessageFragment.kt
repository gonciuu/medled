package com.myapp.medled.screens.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.myapp.medled.R
import com.myapp.medled.databases.real_time_database.DatabaseError
import com.myapp.medled.databases.real_time_database.RealTimeDatabase
import com.myapp.medled.helpers.Helpers
import com.myapp.medled.models.Request
import com.myapp.medled.models.User
import com.myapp.medled.view_models.ChooseDoctorViewModel
import com.myapp.medled.view_models.CurrentUserViewModel
import com.myapp.medled.view_models.RequestViewModel
import kotlinx.android.synthetic.main.fragment_reserve_message.*

class ReserveMessageFragment : Fragment(),DatabaseError {

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

            //set on click on the reserve message button
            reserveMessageButton.setOnClickListener {
                insertRequestToDatabase(doctor)
            }

        })
    }
    //===================================================================================

    //---------------------------| Insert the request to database |---------------------------------
    private fun insertRequestToDatabase(doctor: User){
        val currentUserViewModel: CurrentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)
        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer {
            val patientId = it!!.id
            val request: Request  = Request(patientId+doctor.id,it,doctor,
                isUserActive =true,
                isDoctorActive = false,
                messages = arrayListOf()
            )
            val db:RealTimeDatabase = RealTimeDatabase()
            db.insertRequest(request,requireView(),this)

            //set request in view model to provide it into the message fragment
            val requestViewModel:RequestViewModel = ViewModelProvider(requireActivity()).get(RequestViewModel::class.java)
            requestViewModel.setRequest(request.id)

            findNavController().navigate(R.id.action_reserveMessageFragment_to_chatFragment)
        })
    }
    //==============================================================================================

    //eventual error handler
    override fun errorHandled(errorMessage: String, view: View) {
        Helpers().showSnackBar(errorMessage,view)
    }


}