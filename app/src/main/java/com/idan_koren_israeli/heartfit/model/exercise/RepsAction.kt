package com.idan_koren_israeli.heartfit.model.exercise

import com.idan_koren_israeli.heartfit.model.exercise.Exercise

/**
 * In a workout, this exercise will be tracked by performed repetitions
 */
class RepsAction(exercise: Exercise? = null, private val reps:Int? = null):WorkoutAction(exercise) {

    companion object {
        const val TIME_PER_REP = 4 // In seconds
        // I think that 4 seconds per rep is a good approximation of time.
        // We can also set a specific time-per-rep for each exercise.
    }

    override fun totalTime(): Int {
        if(reps==null)
            return 0;
        return TIME_PER_REP * reps
    }




}