package com.myapp.medled.adapters.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.medled.R
import com.myapp.medled.adapters.recycler_view.view_holders.MessagesRecyclerViewHolderReceiver
import com.myapp.medled.adapters.recycler_view.view_holders.MessagesRecyclerViewHolderSender
import com.myapp.medled.models.Message

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