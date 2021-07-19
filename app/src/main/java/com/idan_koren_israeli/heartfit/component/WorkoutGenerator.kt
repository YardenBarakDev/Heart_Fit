package com.idan_koren_israeli.heartfit.component

import com.google.firebase.firestore.ktx.toObject
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.db.firebase.firestore.FirestoreManager
import com.idan_koren_israeli.heartfit.mvvm.model.*
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment

object WorkoutGenerator {



    fun getWorkoutBreakLength(workout: Workout) : Int {
        return when (workout.workoutLevel) {
            WorkoutLevel.Basic -> 50
            WorkoutLevel.BasicIntermediate -> 45
            WorkoutLevel.Intermediate -> 40
            WorkoutLevel.IntermediateAdvanced -> 35
            WorkoutLevel.Advanced -> 30
        }
    }


    fun getWorkoutTotalLength(workout: Workout) : Int {
        return when (workout.workoutLevel) {
            WorkoutLevel.Basic -> 480 // 8 minutes
            WorkoutLevel.BasicIntermediate -> 720 //12 minutes
            WorkoutLevel.Intermediate -> 960 // 16 minutes
            WorkoutLevel.IntermediateAdvanced -> 1200 // 20 minutes
            WorkoutLevel.Advanced -> 1500 // 25 minutes
        }
    }

    fun getWorkoutRequiredExercisesLevels(workout: Workout) : List<ExerciseLevel> {
        return when (workout.workoutLevel) {
            WorkoutLevel.Basic -> listOf(ExerciseLevel.Basic)
            WorkoutLevel.BasicIntermediate -> listOf(
                ExerciseLevel.Basic,
                ExerciseLevel.Intermediate
            )
            WorkoutLevel.Intermediate -> listOf(ExerciseLevel.Intermediate)
            WorkoutLevel.IntermediateAdvanced -> listOf(
                ExerciseLevel.Intermediate,
                ExerciseLevel.Advanced
            )
            WorkoutLevel.Advanced -> listOf(ExerciseLevel.Advanced)
        }
    }


    fun generateWorkout(workout: Workout, onWorkoutGenerated: (result: List<Exercise>) -> Unit) {

        val requiredLevels = getWorkoutRequiredExercisesLevels(workout)
        val requiredMuscle: List<MuscleGroup> = workout.muscle
        val requiredEquipment: List<Equipment?> = workout.equipment

        val breakLength = getWorkoutBreakLength(workout)
        val targetLength = getWorkoutTotalLength(workout)



        FirestoreManager.loadExercisesByMuscles(requiredMuscle){ loadedExercises->
            // Received all exercises for requested muscles
            val matchedExercises = filterMatchingExercises(loadedExercises,
                requiredLevels,
                requiredEquipment)




            val allWorkoutExercises = mutableListOf<Exercise>()
            var currentWorkoutLength = 0
            var currentIndex = 0

            while(matchedExercises.isNotEmpty() && targetLength >= currentWorkoutLength){
                allWorkoutExercises.add(matchedExercises[currentIndex])
                val breakExercise = Exercise(name = "Break", animationId = 25, timeInSeconds = breakLength, isBreak = true)
                allWorkoutExercises.add(breakExercise)

                currentWorkoutLength+= matchedExercises[currentIndex].timeInSeconds!!
                currentWorkoutLength+= breakLength
                // Adding exercise and then break each time until length is complete

                currentIndex++
                if(currentIndex==matchedExercises.size)
                    currentIndex = 0


            }

            if(allWorkoutExercises.size > 0)
                allWorkoutExercises.removeLast()

            onWorkoutGenerated.invoke(allWorkoutExercises)


        }

    }


    private fun filterMatchingExercises(loadedExercises: List<Exercise>,
                                        requiredLevels : List<ExerciseLevel>,
                                        requiredEquipment : List<Equipment?>) : List<Exercise>{

        val matchedExercises = mutableListOf<Exercise>()

        exercisesLoop@ for (loadedExercise in loadedExercises) {
            if(loadedExercise.level in requiredLevels){

                // Found exercise in the requested level

                if(loadedExercise.equipment!=null){
                    for(equipment in loadedExercise.equipment){
                        if(equipment !in requiredEquipment) {
                            // Not suitable equipment found in this exercise, go to next one
                            continue@exercisesLoop
                        }
                    }
                    matchedExercises.add(loadedExercise) // Equipment meet required
                }
                else
                    matchedExercises.add(loadedExercise) // No equipment constraints
            }
        }

        return matchedExercises
    }


}