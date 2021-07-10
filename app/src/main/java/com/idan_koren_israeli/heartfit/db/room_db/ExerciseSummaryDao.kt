package com.idan_koren_israeli.heartfit.db.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseSummaryDao {

    @Insert
    fun insertAll(exerciseSummary: ExerciseSummary)

    @Delete
    fun delete(exerciseSummary: ExerciseSummary)

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY timestamp DESC")
    fun getAllRunsSortedByDate(uid : String): LiveData<List<ExerciseSummary>>

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY heartsCollected DESC")
    fun getAllSortedByMaXHearts(uid : String): LiveData<List<ExerciseSummary>>

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY caloriesBurned DESC")
    fun getAllSortedByCaloriesBurned(uid : String): LiveData<List<ExerciseSummary>>

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY totalTime DESC")
    fun getAllSortedByTotalTime(uid : String): LiveData<List<ExerciseSummary>>

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY difficulty DESC")
    fun getAllSortedByDifficulty(uid : String): LiveData<List<ExerciseSummary>>

}