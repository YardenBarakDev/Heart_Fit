package com.idan_koren_israeli.heartfit.mvvm.view_model

import androidx.lifecycle.ViewModel
import com.idan_koren_israeli.heartfit.mvvm.repository.TopBarRepository
import kotlin.math.round

object TopBarViewModel : ViewModel() {

    private val topBarRepository = TopBarRepository

    val allHeartFromLastSevenDays = topBarRepository.instance.getAllHeartsCollectedInPastSevenDays()
    val totalTimeFromLastSevenDays = topBarRepository.instance.getTotalTimeOfSevenDaysWorkouts()
    val totalCaloriesBurnedFromLastSevenDays = topBarRepository.instance.getTotalCaloriesBurnedInPastSevenDays()
    val totalWorkoutsFromPastSevenDays = topBarRepository.instance.getSumOfWorkoutsFromPastSevenDays()


    fun calculateHoursFromSeconds(seconds : Int) : String{
        val hours = seconds/360
        val minutes = seconds/60
        if (minutes < 10)
            return "${hours}.0${minutes}"
        return "${hours}.${minutes}"
    }

}