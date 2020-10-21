package com.example.medled.adapters.recycler_view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.MessagesRecyclerViewHolderReceiver
import com.example.medled.adapters.recycler_view.view_holders.MessagesRecyclerViewHolderSender
import com.example.medled.models.Message
import kotlinx.android.synthetic.main.sender_mesage_card.view.*

class MessagesRecyclerViewAdapter(private val currentUserId: String, private val messagesList:ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==0) MessagesRecyclerViewHolderSender(LayoutInflater.from(parent.context).inflate(
            R.layout.sender_mesage_card,parent,false))
        else  MessagesRecyclerViewHolderReceiver(LayoutInflater.from(parent.context).inflate(
            R.layout.receiver_mesage_card,parent,false))
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messagesList[holder.adapterPosition]
        if(message.senderId == currentUserId) (holder as MessagesRecyclerViewHolderSender).sendText.text = message.content
        else (holder as MessagesRecyclerViewHolderReceiver).receiverText.text = message.content
    }

    override fun getItemViewType(position: Int): Int {
        return if(currentUserId == messagesList[position].senderId) 0 else 1
    }
}