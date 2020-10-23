package com.myapp.medled.adapters.recycler_view.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.link_card.view.*

class LinksViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val linkText: TextView = v.linkText
}