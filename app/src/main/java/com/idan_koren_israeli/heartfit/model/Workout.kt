package com.idan_koren_israeli.heartfit.model

import com.idan_koren_israeli.heartfit.model.exercise.Exercise
import com.idan_koren_israeli.heartfit.model.exercise.WorkoutAction
import java.io.Serializable

class Workout(val id:Int? = null, val name:String? = null, private val actions: List<WorkoutAction>? = null) : Serializable {


    fun totalLength():Int{
        if(actions==null)
            return 0;

        // Calculates the total time, similar to python's reduce but with initial value
        return actions.fold(0,{acc, action -> acc + action.totalTime()})
    }
}