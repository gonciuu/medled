package com.example.medled.screens.doctor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medled.R
import com.example.medled.adapters.recycler_view.MessagesRecyclerViewAdapter
import com.example.medled.databases.real_time_database.DatabaseError
import com.example.medled.databases.real_time_database.RealTimeDatabase
import com.example.medled.helpers.Helpers
import com.example.medled.models.Message
import com.example.medled.models.Request
import com.example.medled.view_models.CurrentUserViewModel
import com.example.medled.view_models.RequestViewModel
import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment : Fragment(),ChatInterface,DatabaseError {

    private lateinit var requestViewModel: RequestViewModel
    private lateinit var currentUserViewModel: CurrentUserViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)
        requestViewModel =  ViewModelProvider(requireActivity()).get(RequestViewModel::class.java)

        setChatInfo()
        messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        Helpers().keyboardEnterButtonClick(messageTextInput){
            closeKeyboard()
        }
    }


    //------------------| Get request info from database |----------------------
    private fun setChatInfo(){
        val database = RealTimeDatabase()
        requestViewModel.getRequest().observe(viewLifecycleOwner, Observer {requestId->
            database.getRequestById(requireView(),requestId,this)
        })
    }
    //============================================================================

    //----------------------------| Listen to the request changed |---------------------------------
    override fun onRequestChanged(request: Request) {
        try{
            currentUserViewModel.getUser().observe(viewLifecycleOwner, Observer { currentUser->

                //send message
                sendMessageButton.setOnClickListener {
                    request.messages.add(Message(messageTextInput.text.toString(),currentUser!!.id))
                    messageTextInput.text.clear()
                    closeKeyboard()
                    RealTimeDatabase().insertRequest(request,requireView(),this)
                }

                //set the chat member info
                if(currentUser!!.isDoctor){
                    chatMemberName.text = request.patient!!.name
                    chatMemberBio.text = request.patient.bio
                }else{
                    chatMemberName.text = request.doctor!!.name
                    chatMemberBio.text = request.doctor.medicineBranch
                }

                //set the messages recycler view
                messagesRecyclerView.adapter = MessagesRecyclerViewAdapter(currentUser.id, request.messages)
            })
        }catch (ex:Exception){ }
    }

    //==============================================================================================

    //--------------------| handle eventual db error |----------------------------
    override fun errorHandled(errorMessage: String, view: View) {
        Helpers().showSnackBar(errorMessage,requireView())
    }
    //============================================================================

    //-------------------------| close the soft keyboard |------------------------------
    private fun closeKeyboard(){
        val imm: InputMethodManager? =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
    //===================================================================================
}