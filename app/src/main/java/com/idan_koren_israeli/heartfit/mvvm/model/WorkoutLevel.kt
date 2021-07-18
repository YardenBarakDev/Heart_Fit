package com.idan_koren_israeli.heartfit.mvvm.model

// There are workouts in 5 different levels
enum class WorkoutLevel(val num:Int) {
    Basic(1), BasicIntermediate(2), Intermediate(3), IntermediateAdvanced(4), Advanced(5)
}