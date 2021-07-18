package com.idan_koren_israeli.heartfit.mvvm.view_model

import androidx.lifecycle.ViewModel
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import com.idan_koren_israeli.heartfit.mvvm.repository.HistoryRepository

object WorkoutFinishedViewModel : ViewModel() {

    private var historyRepository = HistoryRepository()


    fun storeWorkoutInDB(workOut : WorkoutSummary){
        historyRepository.storeWorkoutInDB(workOut)
    }
}