package com.idan_koren_israeli.heartfit.recycler.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.mvvm.model.Exercise

class ExercisesDoneHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var caloriesText:TextView
    lateinit var timeText:TextView
    lateinit var nameText:TextView

    init {
        findViews(itemView)
    }

    fun findViews(parent:View){
        caloriesText = parent.findViewById(R.id.exercise_holder_LBL_calories)
        timeText = parent.findViewById(R.id.exercise_holder_LBL_time)
        nameText = parent.findViewById(R.id.exercise_holder_LBL_name)
    }

    fun setExercise(exercise: Exercise) {
        caloriesText.text = exercise.timeInSeconds.toString()
        timeText.text = String.format("%s seconds", exercise.timeInSeconds.toString())
        nameText.text = exercise.name
    }
}