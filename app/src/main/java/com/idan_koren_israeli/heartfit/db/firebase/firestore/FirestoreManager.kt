package com.idan_koren_israeli.heartfit.db.firebase.firestore

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.idan_koren_israeli.heartfit.mvvm.model.*
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment


//RealtimeDatabase
object FirestoreManager {

    @SuppressLint("StaticFieldLeak")
    private lateinit var db: FirebaseFirestore

    private lateinit var exercisesRef: CollectionReference

    private const val KEY_EXERCISE = "Exercise"

    fun initFirestore() {
        db = FirebaseFirestore.getInstance()
        exercisesRef = db.collection(KEY_EXERCISE)
    }


    fun generateWorkout(workout: Workout, onLoaded: (result: List<Exercise>) -> Unit) {

        val requiredLevels = when (workout.workoutLevel) {
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

        val requiredMuscle: List<MuscleGroup> = workout.muscle
        val requiredEquipment: ArrayList<Equipment?> = workout.equipment


        exercisesRef
            .whereArrayContainsAny("muscle", requiredMuscle)
            .get().addOnSuccessListener {

                // Received all exercises for requested muscles
                val matchedExercises: MutableList<Exercise> = mutableListOf()

                exercisesLoop@ for (document in it) {
                    val loadedExercise : Exercise = document.toObject<Exercise>()

                    if(loadedExercise.level in requiredLevels){

                        // Found exercise in the requested level

                        if(loadedExercise.equipment!=null){
                           for(equipment in loadedExercise.equipment){
                               if(equipment !in requiredEquipment) {
                                    // Not suitable equipment found in this exercise, go to next one
                                   continue@exercisesLoop
                               }
                           }
                            matchedExercises.add(document.toObject()) // Equipment meet required
                        }
                        else
                            matchedExercises.add(document.toObject()) // No equipment constraints

                    }

                }

                onLoaded.invoke(matchedExercises)
            }


    }


    fun loadExercises() {
        Log.i("pttt", "Loading Exercises")

        val basicExercise = exercisesRef.whereEqualTo("name", "Bench press")

        exercisesRef.get().addOnSuccessListener { it ->
            val totalCount = it.size()
            basicExercise.get().addOnSuccessListener { basiconly ->
                val basicCount = basiconly.size()

                Log.i("pttt", "Total : $totalCount | basic: $basicCount")
            }
        }

    }

    //(equipmentSelect:EquipmentSelect) -> Unit
    fun loadExercisesByName(name: String, onLoaded: (result: List<Exercise>) -> Unit) {
        val exerciseRef = db.collection(KEY_EXERCISE)
        exerciseRef.whereEqualTo("name", name).get().addOnSuccessListener { documents ->
            val loadedExercises: MutableList<Exercise> = mutableListOf()
            for (document in documents) {
                loadedExercises.add(document.toObject<Exercise>())
            }
            onLoaded.invoke(loadedExercises)
        }
    }

    //(equipmentSelect:EquipmentSelect) -> Unit
    fun loadExercisesByLevel(level: ExerciseLevel, onLoaded: (result: List<Exercise>) -> Unit) {
        val exerciseRef = db.collection(KEY_EXERCISE)
            .whereEqualTo("level", level.name).get().addOnSuccessListener { documents ->
                val loadedExercises: MutableList<Exercise> = mutableListOf()
                for (document in documents) {
                    loadedExercises.add(document.toObject<Exercise>())
                }
                onLoaded.invoke(loadedExercises)
            }
    }


}