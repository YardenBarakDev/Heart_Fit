package com.idan_koren_israeli.heartfit.mvvm.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.idan_koren_israeli.heartfit.mvvm.repository.TopBarRepository

class TopBarViewModel(val uid: String) : ViewModel() {
    var topBarRepository : TopBarRepository = TopBarRepository.instance

    init{
        attachId(uid)
    }


    lateinit var allHeartFromLastSevenDays: LiveData<Int>
    lateinit var totalTimeFromLastSevenDays: LiveData<Int>
    lateinit var totalCaloriesBurnedFromLastSevenDays: LiveData<Int>
    lateinit var totalWorkoutsFromPastSevenDays: LiveData<Int>


    fun calculateHoursFromSeconds(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = seconds / 60
        return if (minutes < 10 && hours < 10)
            "0${hours}:0${minutes}"
        else if (minutes > 10 && hours < 10)
            "0${hours}:${minutes}"
        else if (minutes < 10 && hours > 10)
            "${hours}:0${minutes}"
        else
            "${hours}:${minutes}"
    }

    fun attachId(newUid: String) {
        allHeartFromLastSevenDays = topBarRepository.getAllHeartsCollectedInPastSevenDays(newUid)
        totalTimeFromLastSevenDays = topBarRepository.getTotalTimeOfSevenDaysWorkouts(newUid)
        totalCaloriesBurnedFromLastSevenDays = topBarRepository.getTotalCaloriesBurnedInPastSevenDays(newUid)
        totalWorkoutsFromPastSevenDays = topBarRepository.getSumOfWorkoutsFromPastSevenDays(newUid)
    }


}