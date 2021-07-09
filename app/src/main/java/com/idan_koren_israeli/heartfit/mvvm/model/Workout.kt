package com.idan_koren_israeli.heartfit.mvvm.model

import java.io.Serializable

/**
 * This class includes constraints of a workout and all data about it
 * the exercises themselves are being generated dynamically based on this workout
 */
class Workout(val name:String? = null, val equipment: List<Equipment>,
              val muscle: List<MuscleGroup>, val heartsValue:Int,
              val heartsToUnlock:Int, val workoutLevel: WorkoutLevel) : Serializable {


}