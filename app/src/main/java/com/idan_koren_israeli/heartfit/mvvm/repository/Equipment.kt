package com.idan_koren_israeli.heartfit.mvvm.repository

import com.idan_koren_israeli.heartfit.R

enum class Equipment(val id:Int, val displayName:String, val imageId: Int) {
    DumbbellLight(0,"Light Dumbbells",R.drawable.ic_dumbbell_small),
    DumbbellMedium(1,"Medium Dumbbells",R.drawable.ic_dumbbell),
    DumbbellHeavy (2,"Heavy Dumbbells",R.drawable.ic_dumbbell_large),
    Rope(3,"Rope",R.drawable.ic_mattress_full),
    Mattress(4,"Mattress",R.drawable.ic_skipping_rope),
    Bench(5,"Bench",R.drawable.ic_bench),
    PullBar(6,"Pull Up Bar",R.drawable.ic_pull_up),
    KettlebellLight(7,"Light Kettlebells",R.drawable.ic_kettlebell_small),
    KettlebellMedium(8,"Medium Kettlebells",R.drawable.ic_kettlebell),
    KettlebellHeavy(9,"Heavy Kettlebells", R.drawable.ic_kettlebell_large);

    companion object {
        private val map = values().associateBy(Equipment::id)
        fun fromInt(type: Int) = map[type]
    }
}