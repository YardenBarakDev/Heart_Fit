package com.idan_koren_israeli.heartfit.mvvm.repository

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.db.room_db.HeartFitRoomDB
import java.util.*

class TopBarRepository {

    companion object{
        val instance = TopBarRepository()
    }
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private val exerciseSummaryDao = HeartFitRoomDB.getInstance().exerciseSummaryDao()

    fun getAllHeartsCollectedInPastSevenDays() : LiveData<Int>{
        return exerciseSummaryDao.getAllHeartsCollectedInPastSevenDays(uid!!,Calendar.getInstance().timeInMillis)
    }

    fun getTotalTimeOfSevenDaysWorkouts() : LiveData<Int>{
        return exerciseSummaryDao.getTotalTimeOfSevenDaysWorkouts(uid!!,Calendar.getInstance().timeInMillis)
    }

    fun getTotalCaloriesBurnedInPastSevenDays() : LiveData<Int>{
        return exerciseSummaryDao.getTotalCaloriesBurnedInPastSevenDays(uid!!,Calendar.getInstance().timeInMillis)
    }

    fun getSumOfWorkoutsFromPastSevenDays() : LiveData<Int>{
        return exerciseSummaryDao.getSumOfWorkoutsFromPastSevenDays(uid!!,Calendar.getInstance().timeInMillis)
    }
}