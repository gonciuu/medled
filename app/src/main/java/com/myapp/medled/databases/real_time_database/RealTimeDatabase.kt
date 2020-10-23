package com.myapp.medled.databases.real_time_database

import android.view.View
import com.myapp.medled.helpers.Helpers
import com.myapp.medled.models.Request
import com.myapp.medled.models.User
import com.myapp.medled.screens.doctor.AllDoctorsInterface
import com.myapp.medled.screens.doctor.ChatInterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RealTimeDatabase {
    private val database = FirebaseDatabase.getInstance()
    private val patientsRef = database.getReference("Patients")
    private val doctorsRef = database.getReference("Doctors")
    private val requestsRef = database.getReference("Requests")


    //-------------------------| Insert user to realtime database |--------------------------
    fun insertUserToDatabase(user: User,view: View, errorListener: DatabaseError){
        if(user.isDoctor)
            //user is register as doctor
            doctorsRef.child(user.id.toString()).setValue(user).addOnFailureListener {
                //handle eventual error
                errorListener.errorHandled(it.message.toString(),view)
            }
        else{
            //user is register as patient
            user.medicineBranch = null
            user.starCount = null
            user.startTime = null
            user.endTime = null
            patientsRef.child(user.id.toString()).setValue(user).addOnFailureListener {
                //handle eventual error
                errorListener.errorHandled(it.message.toString(),view)
            }
        }
    }
    //========================================================================================

    //-----------------------| Get all active doctors from database|-------------------------------
    fun getActiveDoctors(view: View,listener: AllDoctorsInterface){
        val arrayListOfDoctors = arrayListOf<User>()

        doctorsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: com.google.firebase.database.DatabaseError) {
               Helpers().showSnackBar(p0.message,view)
            }
            override fun onDataChange(p0: DataSnapshot) {
                arrayListOfDoctors.clear()
                for(i in p0.children){
                    val doctor = i.getValue(User::class.java)!!
                    arrayListOfDoctors.add(doctor)
                }
                listener.onDoctorsDatabaseChanged(arrayListOfDoctors)
            }
        })
    }
    //===========================================================================================


    //-------------------------| Get current user based od uid |-------------------------

    fun getUserById(view: View,id:String,listener: GetCurrentUserInterface){
        var currentUser:User? = null
        patientsRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: com.google.firebase.database.DatabaseError) {
                Helpers().showSnackBar(p0.message,view)
            }
            override fun onDataChange(p0: DataSnapshot) {
                //current user is a patient
                for(i in p0.children){
                    val user = i.getValue(User::class.java)
                    if(user!!.id == id){
                        currentUser = user
                        break
                    }
                }
                //current user is a doctor
                if(currentUser == null){
                    doctorsRef.addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onCancelled(p0: com.google.firebase.database.DatabaseError) {
                            Helpers().showSnackBar(p0.message,view)
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for(i in p0.children) {
                                val user = i.getValue(User::class.java)
                                if (user!!.id == id) {
                                    currentUser = user
                                    break
                                }
                            }
                            listener.onGetCurrentUser(currentUser!!)//doctor
                        }

                    })
                }else{
                    listener.onGetCurrentUser(currentUser!!)//patient
                }
            }
        })
    }

    //====================================================================================

    //------------------| Insert request to database |------------------------
    fun insertRequest(request: Request,view:View,errorListener: DatabaseError){
        requestsRef.child(request.id).setValue(request).addOnFailureListener {
            //handle eventual error
            errorListener.errorHandled(it.message.toString(), view)
        }
    }
    //=========================================================================

    //-----------------------| Get requests from database |---------------------------
    fun getRequests(view:View,listener:AllDoctorsInterface,doctorId:String){
        val listOfRequests = arrayListOf<Request>()
        requestsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: com.google.firebase.database.DatabaseError) {
                Helpers().showSnackBar(p0.message,view)
            }
            //-------check if the doctor id is equal in a request--------
            override fun onDataChange(p0: DataSnapshot) {
                listOfRequests.clear()
                for(i in p0.children){
                    val request = i.getValue(Request::class.java)!!
                    if(request.doctor!!.id == doctorId){
                        listOfRequests.add(request)
                    }
                }
                listener.onRequestsDatabaseChanged(listOfRequests)
            }
        })
        //================================================================
    }
    //==================================================================================




    //----------------------------| Get current request by Id |--------------------------------------
    fun getRequestById(view: View, requestId: String,listener: ChatInterface){
        var request:Request? = null

        requestsRef.child(requestId).addValueEventListener( object: ValueEventListener{
            override fun onCancelled(p0: com.google.firebase.database.DatabaseError) {
                Helpers().showSnackBar(p0.message,view)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val rq = p0.getValue(Request::class.java)
                if(rq!=null){
                    if(rq.id == requestId){
                        request = rq
                    }
                    listener.onRequestChanged(request!!)
                }else{
                    //chat is null
                    listener.onPatientDisabledChat()
                }

            }
        })

    }
    //===============================================================================================


    //----------------------------| Delete and Remove The event listener from the requet |-------------------------------
    fun deleteAndRemoveValueEventListenerFromRequest(requestId: String){
        requestsRef.child(requestId).removeValue()
    }
    //===================================================================================================================
}