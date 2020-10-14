package com.example.medled.screens.medicines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medled.R
import com.example.medled.adapters.recycler_view.MedicinesRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_medicines.*


class MedicinesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_medicines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }
    private fun setupRecyclerView(){
        medicinesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        medicinesRecyclerView.adapter = MedicinesRecyclerViewAdapter()
    }
}