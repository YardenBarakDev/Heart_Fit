package com.idan_koren_israeli.heartfit.model.exercise

import com.idan_koren_israeli.heartfit.model.exercise.Exercise

/**
 * In a workout, exercises will be tracked by an object from this class
 * as an instance of an exercise.
 */
class TimeAction(exercise: Exercise? = null, private val seconds:Int? = null) : WorkoutAction(exercise) {


    override fun totalTime(): Int {
        if(seconds==null) return 0
        return seconds
    }


}