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
        return String.format("%02d:%02d", seconds / 3600,
            (seconds % 3600) / 60)
    }

    fun attachId(newUid: String) {
        allHeartFromLastSevenDays = topBarRepository.getAllHeartsCollectedInPastSevenDays(newUid)
        totalTimeFromLastSevenDays = topBarRepository.getTotalTimeOfSevenDaysWorkouts(newUid)
        totalCaloriesBurnedFromLastSevenDays = topBarRepository.getTotalCaloriesBurnedInPastSevenDays(newUid)
        totalWorkoutsFromPastSevenDays = topBarRepository.getSumOfWorkoutsFromPastSevenDays(newUid)
    }


}