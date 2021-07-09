package com.idan_koren_israeli.heartfit.component

import com.idan_koren_israeli.heartfit.mvvm.model.Workout
import com.pixplicity.easyprefs.library.Prefs

object WorkoutCycleManager{

    private const val HEARTS_KEY : String = "hearts"


    init{

    }

    fun finishWorkout(workout: Workout){
        addHearts(workout.heartsValue)

    }

    private fun addHearts(heartCount: Int){
        var cHearts : Int = getHearts()
        cHearts += heartCount
        Prefs.putInt(HEARTS_KEY, cHearts)
    }

    fun getHearts() : Int{
        return Prefs.getInt(HEARTS_KEY, 0)
    }



}