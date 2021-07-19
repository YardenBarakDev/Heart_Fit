package com.idan_koren_israeli.heartfit.receiver

import android.content.Context
import com.idan_koren_israeli.heartfit.component.MySharedPreferences
import java.util.*

class NotificationSettings {


    companion object{
        val title = "Is it a good time for a workout?"
    }

    fun getMessage(dayStrike : Int) : String{
        return when(dayStrike){
            0 -> "You haven't worked out lately maybe it is a good time to do some exercises"
            in 1..7 -> "Great you worked out ${dayStrike} days in a row, keep the hard work"
            in 7..14 -> "WOW!! you worked out ${dayStrike} days in a row, keep the hard work"
            in 14..28 -> "AMAZING!!!! you worked out ${dayStrike} days in a row, keep the hard work"
            else -> "UNSTOPPABLE! you worked out ${dayStrike} days in a row, keep the hard work"
        }
    }

    fun didWorkOutInPast24Hours(context: Context) : Boolean{
        MySharedPreferences.init(context)
        val lastWorkoutTimeInMS = MySharedPreferences.getLatestTimeStamp()
        val date = Calendar.getInstance()
        date.timeInMillis = lastWorkoutTimeInMS
        val currentDate = Calendar.getInstance()
        return currentDate.get(Calendar.DAY_OF_YEAR) != date.get(Calendar.DAY_OF_YEAR)
    }

    fun updateStrike(context: Context){
        val oneDayInMS= 86400000L
        val currentDayInMS = Calendar.getInstance().timeInMillis
        val lastWorkoutTime = MySharedPreferences.getLatestTimeStamp()

        if (lastWorkoutTime != 0L && currentDayInMS - lastWorkoutTime <= oneDayInMS && didWorkOutInPast24Hours(context)) {
            val lastStrike = MySharedPreferences.getLatestDayStrike()
            MySharedPreferences.setNewDayStrike(lastStrike + 1)
        }
    }

    fun updateReceiverStrike(context: Context){
        val oneDayInMS= 86400000L
        val currentDayInMS = Calendar.getInstance().timeInMillis
        val lastWorkoutTime = MySharedPreferences.getLatestTimeStamp()

        if (lastWorkoutTime == 0L || currentDayInMS - lastWorkoutTime < oneDayInMS)
            MySharedPreferences.setNewDayStrike(0)
    }
}