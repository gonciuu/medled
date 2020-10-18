package com.example.medled.medicine_alarm_receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.medled.MainActivity
import com.example.medled.R
import java.util.*

class MedicineAlarmReceiver : BroadcastReceiver() {
    companion object {
        const val CHANNEL_NAME = "MEDLED CHANNEL"
        const val CHANNEL_ID = "MEDLED_CHANNEL_ID"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        val activityPendingIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Title")
            .setContentText("Message")
            .setSmallIcon(R.drawable.ic_pill)
            .setContentIntent(activityPendingIntent)
            .build()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(),notification)

    }
}