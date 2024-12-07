package com.udit.todoit.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompatSideChannelService
import androidx.core.content.ContextCompat.getSystemService
//import androidx.core.content.ContextCompat.getSystemService
import com.udit.todoit.entry_point.application.MyApp
import javax.inject.Inject

class NotificationHelper @Inject constructor(val myApp: MyApp) {

    data class MyNotification(
        val name: String,
        val description: String,
        val CHANNEL_ID: String
    )

    fun createNecessaryChannels() {
        val notifications: List<MyNotification> = listOf(
            MyNotification(
                name = "TARGET_DATE_TIME_MISSED_NAME",
                description = "When you miss your target date time.",
                CHANNEL_ID ="TARGET_DATE_TIME_MISSED"
            ),
            MyNotification(
                name = "TARGET_DATE_TIME_REMINDER_NAME",
                description = "Reminder prior to target date and time.",
                CHANNEL_ID ="TARGET_DATE_TIME_REMINDER"
            ),
        )
//        val name = getString(R.string.channel_name)
//        val descriptionText = getString(R.string.channel_description)
        notifications.forEach {
            createNotificationChannel(it.name, it.description, it.CHANNEL_ID)
        }
    }

    private fun createNotificationChannel(channelName: String, channelDescription: String, CHANNEL_ID: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                myApp.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}