package com.idan_koren_israeli.heartfit.mvvm.model

import java.io.Serializable

class Exercise(val equipment:List<Equipment> ? = null, val level: ExerciseLevel? =null,
               val muscle: List<MuscleGroup>? = null, val name: String? = null,
               val repeats:Int? =null, val timeInSeconds:Int? = null):Serializable {

}