package com.idan_koren_israeli.heartfit.room_db

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

    @Query("SELECT * FROM exercise_summary")
    fun getAll(): List<ExerciseSummary>

    @Query("SELECT * FROM exercise_summary Where :uid = userId")
    fun getAllById(uid : String): List<ExerciseSummary>

    @Query("SELECT * FROM exercise_summary Where :uid = userId ORDER BY heartsCollected DESC")
    fun getAllSortedByMaXHearts(uid : String): List<ExerciseSummary>

    @Query("SELECT * FROM exercise_summary Where :uid = userId ORDER BY caloriesBurned DESC")
    fun getAllSortedByCaloriesBurned(uid : String): List<ExerciseSummary>

    @Query("SELECT * FROM exercise_summary Where :uid = userId ORDER BY totalTime DESC")
    fun getAllSortedByTotalTime(uid : String): List<ExerciseSummary>

    @Query("SELECT * FROM exercise_summary Where :uid = userId ORDER BY difficulty DESC")
    fun getAllSortedByDifficulty(uid : String): List<ExerciseSummary>

}