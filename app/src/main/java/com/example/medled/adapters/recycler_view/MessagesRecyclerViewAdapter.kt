package com.example.medled.adapters.recycler_view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.MessagesRecyclerViewHolderSender
import kotlinx.android.synthetic.main.sender_mesage_card.view.*

class MessagesRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==0) MessagesRecyclerViewHolderSender(LayoutInflater.from(parent.context).inflate(
            R.layout.sender_mesage_card,parent,false))
        else  MessagesRecyclerViewHolderSender(LayoutInflater.from(parent.context).inflate(
            R.layout.receiver_mesage_card,parent,false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return if(position%2==0) 1 else 0
    }
}