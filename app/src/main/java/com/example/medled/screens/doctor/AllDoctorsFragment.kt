package com.example.medled.screens.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.DoctorTypesRecyclerViewAdapter
import com.example.medled.adapters.recycler_view.DoctorsRecyclerViewAdapter
import com.example.medled.adapters.recycler_view.PatientsRecyclerViewAdapter
import com.example.medled.databases.real_time_database.RealTimeDatabase
import com.example.medled.helpers.Helpers
import com.example.medled.models.DoctorTypeCard
import com.example.medled.models.User
import com.example.medled.view_models.CurrentUserViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_all_doctors.*
import java.lang.Exception


class AllDoctorsFragment : Fragment() , AllDoctorsInterface{

    private lateinit var realTimeDatabase:RealTimeDatabase
    private lateinit var allDoctors: ArrayList<User>
    private lateinit var allPatients: ArrayList<User>

    private var chooseDoctorType = "Pediatrician"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.fragment_all_doctors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        realTimeDatabase = RealTimeDatabase()
        doctorsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val currentUserViewModel: CurrentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)
        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer {user->
            if(user!!.isDoctor){
                allDoctors = ArrayList()

                doctorsTypeRecyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
                doctorsTypeRecyclerView.adapter = DoctorTypesRecyclerViewAdapter(setupDoctorsTypesCards(),this)

                realTimeDatabase.getActiveDoctors(requireView(),this)
            }else{
                doctorsTypeRecyclerView.visibility = View.GONE
                realTimeDatabase.getRequests(requireView(),this,user.id)
            }
        })



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



    override fun chooseDoctor(doctor: User) {
        Helpers().showSnackBar(doctor.toString(),requireView())
    }



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



    override fun onRequestsDatabaseChanged(allPatients: ArrayList<User>) {
        doctorsRecyclerView.adapter = PatientsRecyclerViewAdapter(allPatients,this)
        doctorsAviableText.text = "No patients aviable"
        doctorsAviableText.visibility =if(allPatients.isNotEmpty()) View.GONE else View.VISIBLE
        doctorsRecyclerView.visibility =if(allPatients.isNotEmpty()) View.VISIBLE else View.GONE
    }


}