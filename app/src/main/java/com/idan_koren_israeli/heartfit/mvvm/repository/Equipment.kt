package com.idan_koren_israeli.heartfit.mvvm.repository

import com.idan_koren_israeli.heartfit.R

enum class Equipment(val id:Int, val displayName:String, val imageId: Int) {
    DumbbellLight(0,"Light Dumbbells",R.drawable.ic_dumbbell_small),
    DumbbellMedium(1,"Medium Dumbbells",R.drawable.ic_dumbbell),
    DumbbellHeavy (2,"Heavy Dumbbells",R.drawable.ic_dumbbell_large),
    KettlebellLight(3,"Light Kettlebells",R.drawable.ic_kettlebell_small),
    KettlebellMedium(4,"Medium Kettlebells",R.drawable.ic_kettlebell),
    KettlebellHeavy(5,"Heavy Kettlebells", R.drawable.ic_kettlebell_large),
    Rope(6,"Rope",R.drawable.ic_skipping_rope),
    Mattress(7,"Mattress",R.drawable.ic_mattress_full),
    Bench(8,"Bench",R.drawable.ic_bench),
    PullBar(9,"Pull Up Bar",R.drawable.ic_pull_up);


    companion object {
        private val map = values().associateBy(Equipment::id)
        fun fromInt(type: Int) = map[type]
    }
}