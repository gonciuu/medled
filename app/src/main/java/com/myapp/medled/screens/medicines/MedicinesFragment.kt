package com.myapp.medled.screens.medicines

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.medled.R
import com.myapp.medled.adapters.recycler_view.MedicinesRecyclerViewAdapter
import com.myapp.medled.authentication.Authentication
import com.myapp.medled.databases.medicines_database.Medicine
import com.myapp.medled.databases.medicines_database.MedicinesViewModel
import com.myapp.medled.databases.real_time_database.GetCurrentUserInterface
import com.myapp.medled.databases.real_time_database.RealTimeDatabase
import com.myapp.medled.helpers.Helpers
import com.myapp.medled.helpers.MedicinesCalendar
import com.myapp.medled.medicine_alarm_receiver.MedicineAlarmReceiver
import com.myapp.medled.models.CalendarDay
import com.myapp.medled.models.User
import com.myapp.medled.view_models.CurrentUserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_medicines.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class MedicinesFragment : Fragment(),DeleteMedicineInterface,GetCurrentUserInterface {

    private lateinit var medicinesViewModel: MedicinesViewModel
    private lateinit var allMedicines: List<Medicine>
    //handle last choose day from the calendar
    private lateinit var clickedDay: CalendarDay
    private lateinit var alarmManager: AlarmManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_medicines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set day as first on start
        clickedDay = MedicinesCalendar().getFirstDay()
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        medicinesViewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(MedicinesViewModel::class.java)
        medicinesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        setupNavigation()
        setCalendar()

        //------------------- get all medicines from database and set in into all medicines list --------------------
        medicinesViewModel.allMedicines.observe(viewLifecycleOwner, Observer {
            Log.d("TAG",it.toString())
            allMedicines = it
            //on change all medicines in database set the adapter on recyclerview based on last clicked day
            setupRecyclerView(clickedDay)
        })
        //============================================================================================================


        getCurrentUserId()
    }


    private fun setupNavigation() = addMedicineButton.setOnClickListener {
        findNavController().navigate(R.id.action_medicinesFragment_to_addMedicineFragment)
    }

    //-------------------------| set adapter based on the choose day in calendar |--------------------------
    private fun setupRecyclerView(date: CalendarDay){
        val filterList = ArrayList<Medicine>()
        allMedicines.forEach { medicine->
            val medicineCalendar = Calendar.getInstance()
            medicineCalendar.timeInMillis = medicine.time
            val medicineDay = medicineCalendar.get(Calendar.DAY_OF_MONTH)
            val medicineMonth = medicineCalendar.get(Calendar.MONTH)

            //add to list olny this days which day and month is the same as choose date
            if(medicineDay==date.day && medicineMonth==date.month){
                filterList.add(medicine)
            }
        }

        Collections.sort(filterList,MedicinesArrayListComparator())

        //list of medicines is empty
        if(filterList.isEmpty()){
            addFirstMedicineText.visibility = View.VISIBLE
            medicinesRecyclerView.visibility = View.GONE
        }else{
            //list of medicines has more than 0 elements
            addFirstMedicineText.visibility = View.GONE
            medicinesRecyclerView.visibility = View.VISIBLE
            medicinesRecyclerView.adapter = MedicinesRecyclerViewAdapter(filterList,this)
        }

    }
    //======================================================================================================



    //----------------------| Setup calendar |--------------------------
    private fun setCalendar(){

        //--------------| textvies from calendar layout view |--------------------
        val listOfNumbersTextViews = arrayListOf<TextView>(firstDay,secondDay,thirdDay,fourthDay,fifthDay,sixthDay,seventhDay)
        val listOfDaysLettersTextViews = arrayListOf<TextView>(firstDayLetter,secondDayLetter,thirdDayLetter,fourthDayLetter,fifthDayLetter,sixthDayLetter,seventhDayLetter)
        //=========================================================================

        //the weekdays of the nearest week
        val listOfCalendarDays :ArrayList<CalendarDay> = MedicinesCalendar().getListOfDays()

        listOfNumbersTextViews.forEach { day ->
            //-----------------set data in textviews---------------------
            val actualIndex:Int = listOfNumbersTextViews.indexOf(day)
            day.text = listOfCalendarDays[actualIndex].day.toString()
            listOfDaysLettersTextViews[actualIndex].text = listOfCalendarDays[actualIndex].dayLetter
            //===========================================================

            //-----------handle day click--------------
            day.setOnClickListener {
                //set all days choose to false
                listOfCalendarDays.forEach { it.isChoose = false }
                //set clicked day choose to true
                listOfCalendarDays[actualIndex].isChoose = true
                for(i in 0 until listOfNumbersTextViews.size) {
                    //set colors based on choose property
                       listOfNumbersTextViews[i].backgroundTintList = ContextCompat.getColorStateList(requireActivity(), if(listOfCalendarDays[i].isChoose) R.color.colorPrimary else  R.color.backgroundLightGray)
                       listOfNumbersTextViews[i].setTextColor(ContextCompat.getColor(requireContext(),if(listOfCalendarDays[i].isChoose) R.color.white else R.color.black))
                }
                //Log.d("TAG",listOfCalendarDays[actualIndex].toString())
                clickedDay = listOfCalendarDays[actualIndex]
                setupRecyclerView(listOfCalendarDays[actualIndex])
            }
            //========================================
        }
    }

    //show the alert dialog to confirm medicine delete
    override fun showDeleteDialog(medicine: Medicine) {
        val helpers:Helpers = Helpers()
        try{
            DeleteMedicineDialog(medicine,this).show(requireActivity().supportFragmentManager,"delete_medicine_dialog")
        }catch (isex:IllegalStateException){
            helpers.showSnackBar("Activity cannot be null",requireView())
        }catch (ex:Exception){
            helpers.showSnackBar(ex.message.toString(),requireView())
        }

    }
    
    //delete medicine from the database
    override fun deleteMedicine(medicine: Medicine) {
        medicinesViewModel.deleteMedicine(medicine)

        //remove alarm manager notification
        val intent = Intent(requireActivity().applicationContext, MedicineAlarmReceiver::class.java)

        intent.apply {
            putExtra("medicineName",medicine.name)
            putExtra("medicineAmount",medicine.amount)
            putExtra("medicineType",medicine.type)
            putExtra("medicineImage",medicine.formImage)
        }
        val alarmIntent = intent.let {
            PendingIntent.getBroadcast(requireActivity().applicationContext,medicine.time.toInt(),it,0)
        }
        alarmManager.cancel(alarmIntent)
    }
    //==================================================================


    //--------------------| Get current user uid and save it into viewmodel |------------------------
    private fun getCurrentUserId(){
        val currentUserViewModel:CurrentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)

        currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer {user->
            //if user in viewmodel don't exist get it from database
            if(user==null){
                Log.d("TAG","Wykonało się")
                val authentication = Authentication()
                val id = authentication.getCurrentUserId()
                if(id!=null){
                    RealTimeDatabase().getUserById(requireView(),id,this)
                }else{
                    Helpers().showSnackBar("Something went wrong . Try again later",requireView())
                }
            }else{
                Log.d("TAG",user.id)
            }
        })

    }

    //===============================================================================================

    //--------------------| set current user in view model |--------------------------
    override fun onGetCurrentUser(user: User) {
        try{
            //change bottom nav item text
            if(user.isDoctor)
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).menu.getItem(1).title = "Patients"
            else
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).menu.getItem(1).title = "Doctors"

        }catch (ex:Exception){}
        val currentUserViewModel:CurrentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)
        currentUserViewModel.setUser(user)

    }
    //===============================================================================

}






