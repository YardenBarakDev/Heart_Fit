package com.idan_koren_israeli.heartfit.mvvm.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.idan_koren_israeli.heartfit.mvvm.repository.TopBarRepository

object TopBarViewModel : ViewModel() {

    private val topBarRepository = TopBarRepository

    val allHeartFromLastSevenDays = topBarRepository.instance.getAllHeartsCollectedInPastSevenDays()
    val totalTimeFromLastSevenDays = topBarRepository.instance.getTotalTimeOfSevenDaysWorkouts()
    val totalCaloriesBurnedFromLastSevenDays = topBarRepository.instance.getTotalCaloriesBurnedInPastSevenDays()
    val totalWorkoutsFromPastSevenDays = topBarRepository.instance.getSumOfWorkoutsFromPastSevenDays()


    fun calculateHoursFromSeconds(seconds : Int) : String{
        val hours = seconds/3600
        val minutes = seconds/60
        return if (minutes < 10 && hours < 10)
            "0${hours}:0${minutes}"
        else if (minutes > 10 && hours < 10)
            "0${hours}:${minutes}"
        else if(minutes < 10 && hours > 10)
            "${hours}:0${minutes}"
        else
            "${hours}:${minutes}"
    }

}