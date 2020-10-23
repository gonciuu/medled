package com.myapp.medled.medicine_alarm_receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.myapp.medled.MainActivity
import com.myapp.medled.R

class MedicineAlarmReceiver : BroadcastReceiver() {
    companion object {
        const val CHANNEL_NAME = "MEDLED CHANNEL"
        const val CHANNEL_ID = "MEDLED_CHANNEL_ID"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        //create pending intent to open main activity on the notification click
        val activityPendingIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //create the notification
        val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(intent!!.getStringExtra("medicineName"))
            .setContentText(intent.getStringExtra("medicineAmount") + " "+ intent.getStringExtra("medicineType"))
            .setSmallIcon(intent.getIntExtra("medicineImage",R.drawable.capsule))
            .setContentIntent(activityPendingIntent)
            .setAutoCancel(true)
            .build()

        //create the notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        //show the notification
        notificationManager.notify(System.currentTimeMillis().toInt(),notification)

    }
}