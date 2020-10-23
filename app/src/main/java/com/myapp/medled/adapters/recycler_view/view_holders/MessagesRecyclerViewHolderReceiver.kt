package com.myapp.medled.adapters.recycler_view.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.receiver_mesage_card.view.*

class MessagesRecyclerViewHolderReceiver(v: View):RecyclerView.ViewHolder(v) {
    val receiverText: TextView = v.messageReceiverText
}