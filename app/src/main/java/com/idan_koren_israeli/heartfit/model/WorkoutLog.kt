package com.idan_koren_israeli.heartfit.model

import android.util.Log

const val MET = 4.0f // This is an ~est. of an exercising MET value
// There is not enough academic research today to caculate this number accuritly

// This data should be saved to ROOM-SQL
class WorkoutLog(val workout:Workout? = null, val startTime:Long? = null,
                 var exercisesDone:Int? = null, var caloriesBurned: Int? = null)  {

    fun trackExerciseDone(exercise: Exercise, weight: Float){
        exercisesDone = exercisesDone?.inc()

        caloriesBurned = caloriesBurned?.plus(calcCaloriesBurned(exercise, weight))

        Log.i("pttt", "Up: ${weight} Caloires Total :  $caloriesBurned")
    }

    fun untrackExerciseDone(exercise: Exercise, weight:Float){
        exercisesDone = exercisesDone?.dec()
        caloriesBurned = caloriesBurned?.minus(calcCaloriesBurned(exercise, weight))

    }

    private fun calcCaloriesBurned(exercise: Exercise, weight:Float) : Int{
        //The formula to use is: METs x 3.5 x (your body weight in kilograms) / 200 = calories burned per minute
        return (((MET.times(3.5f).times(weight)).div(200)).times(exercise.timeInSeconds!!.div(60f))).toInt()
    }
}