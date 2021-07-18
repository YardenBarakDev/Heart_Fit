package com.idan_koren_israeli.heartfit.mvvm.model

enum class MuscleGroup {
    CHEST, BACK, ARMS, ABDOMINALS, LEGS, SHOULDERS, GLUTEUS;

    companion object {

        val UPPER_BODY : List<MuscleGroup> = listOf(CHEST, BACK, ARMS, SHOULDERS)
        val LOWER_BODY : List<MuscleGroup> = listOf(GLUTEUS, LEGS)
        val CORE : List<MuscleGroup> = listOf(GLUTEUS, ABDOMINALS)

    }
}