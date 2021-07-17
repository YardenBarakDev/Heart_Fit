package com.idan_koren_israeli.heartfit.mvvm.repository

import com.idan_koren_israeli.heartfit.R

enum class Equipment(val id:Int, val displayName:String, val imageId: Int) {
    DumbbellLight(1,"Light Dumbbells",R.drawable.ic_dumbbell_small),
    DumbbellMedium(2,"Medium Dumbbells",R.drawable.ic_dumbbell),
    DumbbellHeavy (3,"Heavy Dumbbells",R.drawable.ic_dumbbell_large),
    Rope(4,"Rope",R.drawable.ic_mattress_full),
    Mattress(5,"Mattress",R.drawable.ic_skipping_rope),
    Bench(6,"Bench",R.drawable.ic_bench),
    PullBar(7,"Pull Up Bar",R.drawable.ic_pull_up),
    KettlebellLight(8,"Light Kettlebells",R.drawable.ic_kettlebell_small),
    KettlebellMedium(9,"Medium Kettlebells",R.drawable.ic_kettlebell),
    KettlebellHeavy(10,"Heavy Kettlebells", R.drawable.ic_kettlebell_large)
}