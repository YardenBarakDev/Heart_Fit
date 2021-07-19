package com.idan_koren_israeli.heartfit.mvvm.repository


import android.util.Log
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.component.MySharedPreferences
import com.idan_koren_israeli.heartfit.mvvm.model.WorkoutLevel
import com.idan_koren_israeli.heartfit.mvvm.model.MuscleGroup
import com.idan_koren_israeli.heartfit.mvvm.model.Workout

object WorkoutRepository {


    private fun getMyEquipment(): ArrayList<Equipment?> {
        if (MySharedPreferences.getArrayList(CommonUtils.KEY_EQUIPMENT) == null)
            return arrayListOf()

        Log.i("pttt", MySharedPreferences.getArrayList(CommonUtils.KEY_EQUIPMENT)!!.toString() + " ")
        return MySharedPreferences.getArrayList(CommonUtils.KEY_EQUIPMENT)!!
    }


    private fun getAllWorkouts(): List<Workout> {

        val myEquipment = getMyEquipment()

        return listOf(
            Workout(
                "Legs " + WorkoutLevel.Basic.num,
                myEquipment,
                MuscleGroup.LOWER_BODY,
                3,
                0,
                WorkoutLevel.Basic
            ),
            Workout(
                "Arms " + WorkoutLevel.Basic.num,
                myEquipment,
                listOf(MuscleGroup.ARMS),
                2,
                0,
                WorkoutLevel.Basic
            ),
            Workout(
                "Core " + WorkoutLevel.Basic.num,
                myEquipment,
                MuscleGroup.CORE,
                3,
                0,
                WorkoutLevel.Basic
            ),
            Workout(
                "Chest " + WorkoutLevel.Basic.num,
                myEquipment,
                listOf(MuscleGroup.CHEST),
                4,
                0,
                WorkoutLevel.Basic
            ),
            Workout(
                "Full Body " + WorkoutLevel.Basic.num,
                myEquipment,
                MuscleGroup.values().toList(),
                6,
                10,
                WorkoutLevel.Basic
            ),

            Workout(
                "Legs " + WorkoutLevel.BasicIntermediate.num,
                myEquipment,
                listOf(MuscleGroup.LEGS),
                7,
                15,
                WorkoutLevel.BasicIntermediate
            ),
            Workout(
                "Arms " + WorkoutLevel.BasicIntermediate.num,
                myEquipment,
                listOf(MuscleGroup.ARMS),
                6,
                24,
                WorkoutLevel.BasicIntermediate
            ),
            Workout(
                "Core " + WorkoutLevel.BasicIntermediate.num,
                myEquipment,
                MuscleGroup.CORE,
                5,
                32,
                WorkoutLevel.BasicIntermediate
            ),
            Workout(
                "Chest " + WorkoutLevel.BasicIntermediate.num,
                myEquipment,
                listOf(MuscleGroup.CHEST),
                8,
                38,
                WorkoutLevel.BasicIntermediate
            ),
            Workout(
                "Full Body " + WorkoutLevel.BasicIntermediate.num,
                myEquipment,
                MuscleGroup.values().toList(),
                12,
                44,
                WorkoutLevel.BasicIntermediate
            ),

            Workout(
                "Legs " + WorkoutLevel.Intermediate.num,
                myEquipment,
                listOf(MuscleGroup.LEGS),
                12,
                50,
                WorkoutLevel.Intermediate
            ),
            Workout(
                "Arms " + WorkoutLevel.Intermediate.num,
                myEquipment,
                listOf(MuscleGroup.ARMS),
                12,
                50,
                WorkoutLevel.Intermediate
            ),
            Workout(
                "Core " + WorkoutLevel.Intermediate.num,
                myEquipment,
                MuscleGroup.CORE,
                14,
                64,
                WorkoutLevel.Intermediate
            ),
            Workout(
                "Chest " + WorkoutLevel.Intermediate.num,
                myEquipment,
                listOf(MuscleGroup.CHEST),
                14,
                70,
                WorkoutLevel.Intermediate
            ),
            Workout(
                "Full Body " + WorkoutLevel.Intermediate.num,
                myEquipment,
                MuscleGroup.values().toList(),
                18,
                75,
                WorkoutLevel.Intermediate
            ),

            Workout(
                "Legs " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                listOf(MuscleGroup.LEGS),
                15,
                110,
                WorkoutLevel.IntermediateAdvanced
            ),
            Workout(
                "Arms " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                listOf(MuscleGroup.ARMS),
                15,
                125,
                WorkoutLevel.IntermediateAdvanced
            ),
            Workout(
                "Core " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                MuscleGroup.CORE,
                15,
                138,
                WorkoutLevel.IntermediateAdvanced
            ),
            Workout(
                "Chest " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                listOf(MuscleGroup.CHEST),
                16,
                152,
                WorkoutLevel.IntermediateAdvanced
            ),
            Workout(
                "Full Body " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                MuscleGroup.values().toList(),
                22,
                175,
                WorkoutLevel.IntermediateAdvanced
            ),

            Workout(
                "Legs " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                listOf(MuscleGroup.LEGS),
                26,
                200,
                WorkoutLevel.IntermediateAdvanced
            ),
            Workout(
                "Arms " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                listOf(MuscleGroup.ARMS),
                26,
                230,
                WorkoutLevel.IntermediateAdvanced
            ),
            Workout(
                "Core " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                MuscleGroup.CORE,
                25,
                250,
                WorkoutLevel.IntermediateAdvanced
            ),
            Workout(
                "Chest " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                listOf(MuscleGroup.CHEST),
                24,
                263,
                WorkoutLevel.IntermediateAdvanced
            ),
            Workout(
                "Full Body " + WorkoutLevel.IntermediateAdvanced.num,
                myEquipment,
                MuscleGroup.values().toList(),
                30,
                300,
                WorkoutLevel.IntermediateAdvanced
            ),


            Workout(
                "Legs " + WorkoutLevel.Advanced.num,
                myEquipment,
                listOf(MuscleGroup.LEGS),
                30,
                500,
                WorkoutLevel.Advanced
            ),
            Workout(
                "Arms " + WorkoutLevel.Advanced.num,
                myEquipment,
                listOf(MuscleGroup.ARMS),
                32,
                580,
                WorkoutLevel.Advanced
            ),
            Workout(
                "Core " + WorkoutLevel.Advanced.num,
                myEquipment,
                MuscleGroup.CORE,
                35,
                645,
                WorkoutLevel.Advanced
            ),
            Workout(
                "Chest " + WorkoutLevel.Advanced.num,
                myEquipment,
                listOf(MuscleGroup.CHEST),
                38,
                780,
                WorkoutLevel.Advanced
            ),
            Workout(
                "Full Body " + WorkoutLevel.Advanced.num,
                myEquipment,
                MuscleGroup.values().toList(),
                50,
                1000,
                WorkoutLevel.Advanced
            ),

            )
    }


    fun getWorkoutsTree(): MutableList<ArrayList<Workout>> {

        val allWorkouts = getAllWorkouts()
        return mutableListOf<ArrayList<Workout>>(
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
            arrayListOf(allWorkouts[25], allWorkouts[26]),
            arrayListOf(allWorkouts[27], allWorkouts[28]),
            arrayListOf(allWorkouts[29]),
        )

    }


}