package com.idan_koren_israeli.heartfit.model.exercise


/**
 * The exercises instances in the workout will be represented by workout actions
 */
abstract class WorkoutAction(val exercise: Exercise? = null) {

    /**
     * Returns the time in seconds of this action
     */
    abstract fun totalTime() : Int

}