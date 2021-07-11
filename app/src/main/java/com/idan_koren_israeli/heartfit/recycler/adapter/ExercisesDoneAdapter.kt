package com.idan_koren_israeli.heartfit.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.mvvm.model.Exercise
import com.idan_koren_israeli.heartfit.recycler.holder.EquipmentSelectHolder
import com.idan_koren_israeli.heartfit.recycler.holder.ExercisesDoneHolder

class ExercisesDoneAdapter(context: Context, exercises: ArrayList<Exercise>,
                           private val userWeight: Float
) : RecyclerView.Adapter<ExercisesDoneHolder>() {

    var data : ArrayList<Exercise> = arrayListOf()
    private val mInflater:LayoutInflater = LayoutInflater.from(context)

    init{
        data.addAll(exercises)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesDoneHolder {
        val view: View = mInflater.inflate(R.layout.holder_exercise_done,parent, false)

        return ExercisesDoneHolder(view)
    }

    override fun onBindViewHolder(holder: ExercisesDoneHolder, position: Int) {
        holder.setExercise(data[position], userWeight)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}