package com.idan_koren_israeli.heartfit.firebase.firestore

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.idan_koren_israeli.heartfit.model.Exercise
import com.idan_koren_israeli.heartfit.model.ExerciseLevel
import com.idan_koren_israeli.heartfit.model.Workout


//RealtimeDatabase
object FirestoreManager {

    @SuppressLint("StaticFieldLeak")
    private lateinit var db: FirebaseFirestore

    fun initFirestore(){
        db = FirebaseFirestore.getInstance()
    }

    private val KEY_EXERCISE = "Exercise"

    fun loadExercises(){
        Log.i("pttt", "Loading Exercises")
        val exerciseRef = db.collection(KEY_EXERCISE)

        val basicExercise = exerciseRef.whereEqualTo("name", "Bench press")

        exerciseRef.get().addOnSuccessListener { it ->
            val totalCount = it.size()
            basicExercise.get().addOnSuccessListener { basiconly ->
                val basicCount = basiconly.size()

                Log.i("pttt", "Total : $totalCount | basic: $basicCount")
            }
        }

    }

    //(equipmentSelect:EquipmentSelect) -> Unit
    fun loadExercisesByName(name: String, onLoaded: (result: List<Exercise>) -> Unit){
        val exerciseRef = db.collection(KEY_EXERCISE)
        exerciseRef.whereEqualTo("name", name).get().addOnSuccessListener {
                documents ->
            val loadedExercises:MutableList<Exercise> = mutableListOf()
            for(document in documents){
                loadedExercises.add(document.toObject<Exercise>())
            }
            onLoaded.invoke(loadedExercises)
        }
    }

    //(equipmentSelect:EquipmentSelect) -> Unit
    fun loadExercisesByLevel(level: ExerciseLevel, onLoaded: (result: List<Exercise>) -> Unit){
        val exerciseRef = db.collection(KEY_EXERCISE)
        exerciseRef.whereEqualTo("level", level.name).get().addOnSuccessListener {
                documents ->
            val loadedExercises:MutableList<Exercise> = mutableListOf()
            for(document in documents){
                loadedExercises.add(document.toObject<Exercise>())
            }
            onLoaded.invoke(loadedExercises)
        }
    }



    fun generateWorkout(){
        //TODO generation algorithm, based on time, equipment, level, muscle
    }

}