package com.idan_koren_israeli.heartfit.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.component.MySharedPreferences
import com.idan_koren_israeli.heartfit.mvvm.view.activity.MainActivity

class HeartFitReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null){
            updateStrike(context)

            //notification message
            val content = getMessageContent()

            //pending intent
            val pendingIntent = createPendingIntent(context)

            //create notification
            val builder =
                NotificationCompat.Builder(context, "HeartFit")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(NotificationSettings.title)
                    .setContentText(content)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)

            NotificationManagerCompat.from(context).notify(42,builder.build())

        }
    }

    private fun updateStrike(context: Context) {
        val notificationSettings = NotificationSettings()
        notificationSettings.updateReceiverStrike(context)
    }

    private fun getMessageContent(): String {
        val daysStrike = MySharedPreferences.getLatestDayStrike()
        val notificationSettings = NotificationSettings()
        return notificationSettings.getMessage(daysStrike)
    }

    private fun createPendingIntent(context: Context): PendingIntent {
        val i = Intent(context, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK

        return PendingIntent.getActivity(context, 0, i, 0)
    }


}