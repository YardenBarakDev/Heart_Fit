package com.idan_koren_israeli.heartfit.model

import com.idan_koren_israeli.heartfit.model.exercise.Exercise
import com.idan_koren_israeli.heartfit.model.exercise.WorkoutAction

class Workout(val id:Int? = null, val name:String? = null, val actions: List<WorkoutAction>? = null) {


    fun totalLength():Int{
        if(actions==null)
            return 0;

        // Calculates the total time, similar to python's reduce but with initial value
        return actions.fold(0,{acc, action -> acc + action.totalTime()})
    }
}