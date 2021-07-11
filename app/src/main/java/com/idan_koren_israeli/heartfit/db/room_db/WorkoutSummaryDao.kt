package com.idan_koren_israeli.heartfit.db.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WorkoutSummaryDao {

    @Insert
    fun insertAll(workoutSummary: WorkoutSummary)

    @Delete
    fun delete(workoutSummary: WorkoutSummary)

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY timestamp DESC")
    fun getAllRunsSortedByDate(uid : String): LiveData<List<WorkoutSummary>>

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY heartsCollected DESC")
    fun getAllSortedByMaXHearts(uid : String): LiveData<List<WorkoutSummary>>

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY caloriesBurned DESC")
    fun getAllSortedByCaloriesBurned(uid : String): LiveData<List<WorkoutSummary>>

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY totalDurationSeconds DESC")
    fun getAllSortedByTotalDurationSeconds(uid : String): LiveData<List<WorkoutSummary>>

    @Query("SELECT * FROM workout_summary Where :uid = userId ORDER BY difficulty DESC")
    fun getAllSortedByDifficulty(uid : String): LiveData<List<WorkoutSummary>>

}