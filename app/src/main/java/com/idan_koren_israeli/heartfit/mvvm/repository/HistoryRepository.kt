package com.idan_koren_israeli.heartfit.mvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import com.idan_koren_israeli.heartfit.db.room_db.HeartFitRoomDB

class HistoryRepository : ViewModel() {

    companion object{
        val instance = HistoryRepository()
        val uid = FirebaseAuth.getInstance().currentUser
    }
    private val exerciseSummaryDao = HeartFitRoomDB.getInstance().exerciseSummaryDao()

    fun getInstance() : HistoryRepository{return instance}

    fun getAllExerciseSummarySortedByDate() : LiveData<List<WorkoutSummary>>{
        return exerciseSummaryDao.getAllRunsSortedByDate(uid.toString())
    }

    fun getAllExerciseSummarySortedByCaloriesBurned() : LiveData<List<WorkoutSummary>>{
        return exerciseSummaryDao.getAllSortedByCaloriesBurned(uid.toString())
    }

    fun getAllExerciseSummarySortedByDifficulty() : LiveData<List<WorkoutSummary>>{
        return exerciseSummaryDao.getAllSortedByDifficulty(uid.toString())
    }

    fun getAllExerciseSummarySortedByMaXHearts() : LiveData<List<WorkoutSummary>>{
        return exerciseSummaryDao.getAllSortedByMaXHearts(uid.toString())
    }

    fun getAllExerciseSummarySortedByTotalTime() : LiveData<List<WorkoutSummary>>{
        return exerciseSummaryDao.getAllSortedByTotalDurationSeconds(uid.toString())
    }

}