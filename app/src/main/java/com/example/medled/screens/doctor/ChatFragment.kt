package com.example.medled.screens.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medled.R
import com.example.medled.adapters.recycler_view.MessagesRecyclerViewAdapter
import com.example.medled.view_models.RequestViewModel
import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment : Fragment() {

    private lateinit var requestViewModel: RequestViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestViewModel =  ViewModelProvider(requireActivity()).get(
            RequestViewModel::class.java)
        setChatInfo()


        messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        messagesRecyclerView.adapter = MessagesRecyclerViewAdapter()
    }


    private fun setChatInfo(){
        requestViewModel.getRequest().observe(viewLifecycleOwner, Observer {requestId->




        })

    }

}