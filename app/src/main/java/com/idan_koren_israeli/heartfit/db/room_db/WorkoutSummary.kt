package com.idan_koren_israeli.heartfit.db.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_summary")
data class WorkoutSummary(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var timestamp: Long,
    var userId : String,
    var heartsCollected : Int,
    var caloriesBurned : Int,
    var totalDurationSeconds : Int,
    var difficulty : String,
    var muscles : String
)


