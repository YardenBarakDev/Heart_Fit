package com.idan_koren_israeli.heartfit.mvvm.repository

import android.util.Log
import com.google.gson.reflect.TypeToken
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.component.KotlinPrefsManager
import com.idan_koren_israeli.heartfit.mvvm.model.WorkoutLevel
import com.idan_koren_israeli.heartfit.mvvm.model.MuscleGroup
import com.idan_koren_israeli.heartfit.mvvm.model.Workout
import com.idankorenisraeli.mysettingsscreen.MySettingsScreen
import com.idankorenisraeli.mysettingsscreen.common.SharedPrefsManager
import com.idankorenisraeli.mysettingsscreen.tile_data.dialog.MultiChoiceTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.SavableTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.SettingsTileData

object WorkoutRepository {


    private fun getMyEquipment(): List<Equipment> {
        val noneEquipment = ArrayList<Boolean>()
        noneEquipment.add(true)

        val myPrefsEquipment = KotlinPrefsManager.getArrayList(CommonUtils.KEY_EQUIPMENT)

        Log.i("pttt", "Loading myEquipment"  + myPrefsEquipment.toString())
        return listOf()
    }


    val allWorkouts = listOf<Workout>(
        Workout(
            "Legs " + WorkoutLevel.Basic.num,
            getMyEquipment(),
            listOf(MuscleGroup.LEGS),
            3,
            0,
            WorkoutLevel.Basic
        ),
        Workout(
            "Arms " + WorkoutLevel.Basic.num,
            getMyEquipment(),
            listOf(MuscleGroup.ARMS),
            2,
            0,
            WorkoutLevel.Basic
        ),
        Workout(
            "Core " + WorkoutLevel.Basic.num,
            getMyEquipment(),
            MuscleGroup.CORE,
            3,
            0,
            WorkoutLevel.Basic
        ),
        Workout(
            "Chest " + WorkoutLevel.Basic.num,
            getMyEquipment(),
            listOf(MuscleGroup.CHEST),
            4,
            0,
            WorkoutLevel.Basic
        ),
        Workout(
            "Full Body " + WorkoutLevel.Basic.num,
            getMyEquipment(),
            MuscleGroup.values().toList(),
            6,
            10,
            WorkoutLevel.Basic
        ),

        Workout(
            "Legs " + WorkoutLevel.BasicIntermediate.num,
            getMyEquipment(),
            listOf(MuscleGroup.LEGS),
            7,
            15,
            WorkoutLevel.BasicIntermediate
        ),
        Workout(
            "Arms " + WorkoutLevel.BasicIntermediate.num,
            getMyEquipment(),
            listOf(MuscleGroup.ARMS),
            6,
            24,
            WorkoutLevel.BasicIntermediate
        ),
        Workout(
            "Core " + WorkoutLevel.BasicIntermediate.num,
            getMyEquipment(),
            MuscleGroup.CORE,
            5,
            32,
            WorkoutLevel.BasicIntermediate
        ),
        Workout(
            "Chest " + WorkoutLevel.BasicIntermediate.num,
            getMyEquipment(),
            listOf(MuscleGroup.CHEST),
            8,
            38,
            WorkoutLevel.BasicIntermediate
        ),
        Workout(
            "Full Body " + WorkoutLevel.BasicIntermediate.num,
            getMyEquipment(),
            MuscleGroup.values().toList(),
            12,
            44,
            WorkoutLevel.BasicIntermediate
        ),

        Workout(
            "Legs " + WorkoutLevel.Intermediate.num,
            getMyEquipment(),
            listOf(MuscleGroup.LEGS),
            12,
            50,
            WorkoutLevel.Intermediate
        ),
        Workout(
            "Arms " + WorkoutLevel.Intermediate.num,
            getMyEquipment(),
            listOf(MuscleGroup.ARMS),
            12,
            50,
            WorkoutLevel.Intermediate
        ),
        Workout(
            "Core " + WorkoutLevel.Intermediate.num,
            getMyEquipment(),
            MuscleGroup.CORE,
            14,
            64,
            WorkoutLevel.Intermediate
        ),
        Workout(
            "Chest " + WorkoutLevel.Intermediate.num,
            getMyEquipment(),
            listOf(MuscleGroup.CHEST),
            14,
            70,
            WorkoutLevel.Intermediate
        ),
        Workout(
            "Full Body " + WorkoutLevel.Intermediate.num,
            getMyEquipment(),
            MuscleGroup.values().toList(),
            18,
            75,
            WorkoutLevel.Intermediate
        ),

        Workout(
            "Legs " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            listOf(MuscleGroup.LEGS),
            15,
            110,
            WorkoutLevel.IntermediateAdvanced
        ),
        Workout(
            "Arms " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            listOf(MuscleGroup.ARMS),
            15,
            125,
            WorkoutLevel.IntermediateAdvanced
        ),
        Workout(
            "Core " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            MuscleGroup.CORE,
            15,
            138,
            WorkoutLevel.IntermediateAdvanced
        ),
        Workout(
            "Chest " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            listOf(MuscleGroup.CHEST),
            16,
            152,
            WorkoutLevel.IntermediateAdvanced
        ),
        Workout(
            "Full Body " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            MuscleGroup.values().toList(),
            22,
            175,
            WorkoutLevel.IntermediateAdvanced
        ),

        Workout(
            "Legs " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            listOf(MuscleGroup.LEGS),
            26,
            200,
            WorkoutLevel.IntermediateAdvanced
        ),
        Workout(
            "Arms " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            listOf(MuscleGroup.ARMS),
            26,
            230,
            WorkoutLevel.IntermediateAdvanced
        ),
        Workout(
            "Core " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            MuscleGroup.CORE,
            25,
            250,
            WorkoutLevel.IntermediateAdvanced
        ),
        Workout(
            "Chest " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            listOf(MuscleGroup.CHEST),
            24,
            263,
            WorkoutLevel.IntermediateAdvanced
        ),
        Workout(
            "Full Body " + WorkoutLevel.IntermediateAdvanced.num,
            getMyEquipment(),
            MuscleGroup.values().toList(),
            30,
            300,
            WorkoutLevel.IntermediateAdvanced
        ),

        )


    val workoutByRows = mutableListOf<ArrayList<Workout>>(
        arrayListOf(allWorkouts[0]), // Row
        arrayListOf(allWorkouts[1], allWorkouts[2]), // Row
        arrayListOf(allWorkouts[3]), // Row
        arrayListOf(allWorkouts[4]), // Row
        arrayListOf(allWorkouts[5], allWorkouts[6]), // Row
        arrayListOf(allWorkouts[7], allWorkouts[8]), // Row
        arrayListOf(allWorkouts[9]), // Row
        arrayListOf(allWorkouts[10], allWorkouts[11], allWorkouts[12]), // Row
        arrayListOf(allWorkouts[13], allWorkouts[14]), // Row
        arrayListOf(allWorkouts[15]), // Row
        arrayListOf(allWorkouts[16], allWorkouts[17]), // Row
        arrayListOf(allWorkouts[18], allWorkouts[19]), // Row
        arrayListOf(allWorkouts[20]),
        arrayListOf(allWorkouts[21], allWorkouts[22], allWorkouts[23]),
        arrayListOf(allWorkouts[24]),
    )

}