package com.example.medled.screens.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.DoctorTypesRecyclerViewAdapter
import com.example.medled.adapters.recycler_view.DoctorsRecyclerViewAdapter
import com.example.medled.databases.real_time_database.RealTimeDatabase
import com.example.medled.models.DoctorTypeCard
import com.example.medled.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        allDoctors = ArrayList()
        realTimeDatabase = RealTimeDatabase()

        doctorsTypeRecyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        doctorsTypeRecyclerView.adapter = DoctorTypesRecyclerViewAdapter(setupDoctorsTypesCards(),this)

        doctorsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        realTimeDatabase.getActiveDoctors(requireView(),this)

    }

    //--------------------------------| get all doctors cards |---------------------------------------
    private fun setupDoctorsTypesCards():ArrayList<DoctorTypeCard>{
        return arrayListOf<DoctorTypeCard>(
            DoctorTypeCard("Pediatrician",R.drawable.doctor_avatar_1,true),
            DoctorTypeCard("Neurologist",R.drawable.doctor_avatar_1,false),
            DoctorTypeCard("Family Doctor",R.drawable.doctor_avatar_1,false),
            DoctorTypeCard("Psychiatrist",R.drawable.doctor_avatar_1,false),
            DoctorTypeCard("Pulmonologist",R.drawable.doctor_avatar_1,false),
            DoctorTypeCard("Dermatologist",R.drawable.doctor_avatar_1,false),
            DoctorTypeCard("Cardiologist",R.drawable.doctor_avatar_1,false)
        )
    }
    //================================================================================================

    //----------------| Change doctor type |-------------------
    override fun changeType(doctorType: String) {
        chooseDoctorType = doctorType
        val listOfChooseBranchDoctors = getListOfDoctorsBasedOnDoctorType()
        doctorsRecyclerView.adapter = DoctorsRecyclerViewAdapter(listOfChooseBranchDoctors)
    }
    //=========================================================



    override fun chooseDoctor(doctor: User) {

    }



    //-------------------------| Listening to the database active doctors |---------------------------------
    override fun onDoctorsDatabaseChanged(allDoctors: ArrayList<User>) {
        this.allDoctors = allDoctors
        try{
            val listOfChooseBranchDoctors = getListOfDoctorsBasedOnDoctorType()
            doctorsRecyclerView.adapter = DoctorsRecyclerViewAdapter(listOfChooseBranchDoctors)
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
}