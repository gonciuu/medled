package com.example.medled.screens.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.DoctorTypesRecyclerViewAdapter
import com.example.medled.adapters.recycler_view.DoctorsRecyclerViewAdapter
import com.example.medled.adapters.recycler_view.PatientsRecyclerViewAdapter
import com.example.medled.databases.real_time_database.RealTimeDatabase
import com.example.medled.models.DoctorTypeCard
import com.example.medled.models.Request
import com.example.medled.models.User
import com.example.medled.view_models.ChooseDoctorViewModel
import com.example.medled.view_models.CurrentUserViewModel
import kotlinx.android.synthetic.main.fragment_all_doctors.*
import java.lang.Exception


class AllDoctorsFragment : Fragment() , AllDoctorsInterface{

    private lateinit var realTimeDatabase:RealTimeDatabase
    private lateinit var allDoctors: ArrayList<User>

    private var chooseDoctorType = "Pediatrician"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.fragment_all_doctors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        realTimeDatabase = RealTimeDatabase()
        doctorsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val currentUserViewModel: CurrentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)
        //---------------------------------| Check the user type (doctor or patient) |--------------------------------------
        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer {user->
            try {
                if(!user!!.isDoctor){
                    //if user is a patient show the doctor list
                    allDoctors = ArrayList()
                    doctorsTypeRecyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
                    doctorsTypeRecyclerView.adapter = DoctorTypesRecyclerViewAdapter(setupDoctorsTypesCards(),this)
                    realTimeDatabase.getActiveDoctors(requireView(),this)
                }else{
                    //if user is a doctor show a patients list
                    doctorsTypeRecyclerView.visibility = View.GONE
                    realTimeDatabase.getRequests(requireView(),this,user.id)
                }
            }catch (ex:Exception){}
        })
        //====================================================================================================================

    }

    //--------------------------------| get all doctors cards |---------------------------------------
    private fun setupDoctorsTypesCards():ArrayList<DoctorTypeCard>{
        return arrayListOf<DoctorTypeCard>(
            DoctorTypeCard("Pediatrician",R.drawable.pediatrican_icon,true),
            DoctorTypeCard("Neurologist",R.drawable.neurologist_icon,false),
            DoctorTypeCard("Family Doctor",R.drawable.family_doctor_icon,false),
            DoctorTypeCard("Psychiatrist",R.drawable.psychiatrist_icon,false),
            DoctorTypeCard("Pulmonologist",R.drawable.pulmonologist_icon,false),
            DoctorTypeCard("Dermatologist",R.drawable.dermatologist_icon,false),
            DoctorTypeCard("Cardiologist",R.drawable.cardiologist_icon,false)
        )
    }
    //================================================================================================

    //----------------| Change doctor type |-------------------
    override fun changeType(doctorType: String) {
        chooseDoctorType = doctorType
        val listOfChooseBranchDoctors = getListOfDoctorsBasedOnDoctorType()
        doctorsRecyclerView.adapter = DoctorsRecyclerViewAdapter(listOfChooseBranchDoctors,this)

        doctorsAviableText.visibility = if(listOfChooseBranchDoctors.isNotEmpty()) View.GONE else View.VISIBLE
        doctorsRecyclerView.visibility = if(listOfChooseBranchDoctors.isNotEmpty()) View.VISIBLE else View.GONE
    }
    //=========================================================



    //----------------------| Set the choose doctor in viewmoel to et data in next layout - go to reserve message screen |-----------------------------
    override fun chooseDoctor(doctor: User) {
        val chooseDoctorViewModel: ChooseDoctorViewModel = ViewModelProvider(requireActivity()).get(ChooseDoctorViewModel::class.java)
        chooseDoctorViewModel.setDoctor(doctor)
        findNavController().navigate(R.id.action_allDoctorsFragment_to_reserveMessageFragment)
    }
    //==================================================================================================================================================



    //-------------------------| Listening to the database active doctors |---------------------------------
    override fun onDoctorsDatabaseChanged(allDoctors: ArrayList<User>) {
        this.allDoctors = allDoctors
        try{
            val listOfChooseBranchDoctors = getListOfDoctorsBasedOnDoctorType()
            doctorsRecyclerView.adapter = DoctorsRecyclerViewAdapter(listOfChooseBranchDoctors,this)
            doctorsAviableText.visibility =if(listOfChooseBranchDoctors.isNotEmpty()) View.GONE else View.VISIBLE
            doctorsRecyclerView.visibility =if(listOfChooseBranchDoctors.isNotEmpty()) View.VISIBLE else View.GONE
        }catch (ex:Exception){}
    }

    //======================================================================================================

    //-----------| Get all doctors which type is the same as choosen |-------------
    private fun getListOfDoctorsBasedOnDoctorType():ArrayList<User>{
        val listOfChooseBranchDoctors = ArrayList<User>()
        allDoctors.forEach {
            if(it.medicineBranch == chooseDoctorType){
                listOfChooseBranchDoctors.add(it)
            }
        }
        return listOfChooseBranchDoctors
    }
    //===============================================================================




    //-------------------------| Listen to requests database changed |------------------------------
    override fun onRequestsDatabaseChanged(allRequests: ArrayList<Request>) {
        doctorsRecyclerView.adapter = PatientsRecyclerViewAdapter(allRequests,this)
        doctorsAviableText.text = "No patients aviable"
        textView17.text = "Your patients"
        doctorsAviableText.visibility =if(allRequests.isNotEmpty()) View.GONE else View.VISIBLE
        doctorsRecyclerView.visibility =if(allRequests.isNotEmpty()) View.VISIBLE else View.GONE
    }
    //==============================================================================================


}