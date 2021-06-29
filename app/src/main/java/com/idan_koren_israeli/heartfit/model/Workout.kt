package com.idan_koren_israeli.heartfit.model

import com.idan_koren_israeli.heartfit.model.exercise.Exercise
import com.idan_koren_israeli.heartfit.model.exercise.WorkoutAction
import java.io.Serializable

class Workout(val id:Int? = null, val name:String? = null, val actions: List<WorkoutAction>, val heartsValue:Int, val heartsToUnlock:Int) : Serializable {


    fun totalLength():Int{
        // Calculates the total time, similar to python's reduce but with initial value
        return actions.fold(0,{acc, action -> acc + action.totalTime()})
    }
}