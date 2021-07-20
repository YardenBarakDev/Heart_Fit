package com.idan_koren_israeli.heartfit.mvvm.model

import android.util.Log
import java.io.Serializable

const val MET = 4.0f // This is an ~est. of an exercising MET value
// There is not enough academic research today to caculate this number accuritly

class WorkoutLog(val workout:Workout? = null, var totalDuration:Int = 0,
                 var exercisesDone: ArrayList<Exercise> = arrayListOf(), var caloriesBurned: Int? = null,
                var totalExercisesCount : Int = 0, var exercisesSkipped : Int = 0) : Serializable  {

    fun trackExerciseDone(exercise: Exercise, weight: Float){
        exercisesDone.add(exercise)

        caloriesBurned = caloriesBurned?.plus(calcCaloriesBurned(exercise, weight))

        Log.i("pttt", "Up: ${weight} Caloires Total :  $caloriesBurned")
    }

    fun untrackExerciseDone(exercise: Exercise, weight:Float){
        exercisesDone.remove(exercise)
        caloriesBurned = caloriesBurned?.minus(calcCaloriesBurned(exercise, weight))

    }

    companion object{
        fun calcCaloriesBurned(exercise: Exercise, weight:Float) : Int{
            //The formula to use is: METs x 3.5 x (your body weight in kilograms) / 200 = calories burned per minute
            return (((MET.times(3.5f).times(weight)).div(200)).times(exercise.timeInSeconds!!.div(60f))).toInt()
        }
    }

}