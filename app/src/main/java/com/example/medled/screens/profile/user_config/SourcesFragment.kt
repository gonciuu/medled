package com.example.medled.screens.profile.user_config

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medled.R
import com.example.medled.adapters.recycler_view.LinksRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_sources.*


class SourcesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourcesBackButton.setOnClickListener { requireActivity().onBackPressed() }
        setupLinksRecyclerViews()
    }

    //---------------------------| Setup links adapters |---------------------------------
    private fun setupLinksRecyclerViews(){
        layoutInspirationsSourcesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        imagesSourcesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val inspirationsAdapter = LinksRecyclerViewAdapter(arrayListOf("a","b","c","d"))
        val imagesSources = LinksRecyclerViewAdapter(arrayListOf("a","b","c","d"))

        layoutInspirationsSourcesRecyclerView.adapter = inspirationsAdapter
        imagesSourcesRecyclerView.adapter = imagesSources
    }


}