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

    // 604800000 is one week in ms
    @Query("SELECT * FROM workout_summary Where :uid = userId AND :currentTimeStamp - timestamp <= 604800000 ORDER BY timestamp DESC")
    fun getAllRunsFromPastSevenDays(uid : String, currentTimeStamp : Long): LiveData<List<WorkoutSummary>>

    //2592000000  is 30 days in ms
    @Query("SELECT * FROM workout_summary Where :uid = userId AND :currentTimeStamp - timestamp <= 2592000000 ORDER BY timestamp DESC")
    fun getAllRunsFromPastMonth(uid : String, currentTimeStamp : Long): LiveData<List<WorkoutSummary>>
}