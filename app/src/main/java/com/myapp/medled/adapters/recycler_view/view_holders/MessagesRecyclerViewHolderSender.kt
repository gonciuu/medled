package com.myapp.medled.adapters.recycler_view.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sender_mesage_card.view.*

class MessagesRecyclerViewHolderSender(v:View):RecyclerView.ViewHolder(v) {
    val sendText: TextView = v.messageSenderText
}