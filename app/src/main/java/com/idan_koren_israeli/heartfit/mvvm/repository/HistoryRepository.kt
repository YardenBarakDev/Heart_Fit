package com.idan_koren_israeli.heartfit.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.db.room_db.HeartFitRoomDB
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import org.jetbrains.anko.doAsync
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class HistoryRepository : ViewModel() {

    companion object{
        val instance = HistoryRepository()
        val uid = FirebaseAuth.getInstance().currentUser
    }
    private val exerciseSummaryDao = HeartFitRoomDB.getInstance().exerciseSummaryDao()

    fun getInstance() : HistoryRepository{return instance}

    fun storeWorkoutInDB(workOut : WorkoutSummary){
        val executor: Executor = Executors.newSingleThreadExecutor()
        executor.execute {
            exerciseSummaryDao.insertAll(workOut)
            Log.i("pttt", "Inserting "  + workOut.heartsCollected)
            // TODO Add workout to roomdb
        }
    }
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

    fun getAllRunsFromPastSevenDays() : LiveData<List<WorkoutSummary>>{
        return exerciseSummaryDao.getAllRunsFromPastSevenDays(uid.toString(), Calendar.getInstance().timeInMillis)
    }

    fun getAllRunsFromPastMonth() : LiveData<List<WorkoutSummary>>{
        return exerciseSummaryDao.getAllRunsFromPastMonth(uid.toString(), Calendar.getInstance().timeInMillis)
    }
}