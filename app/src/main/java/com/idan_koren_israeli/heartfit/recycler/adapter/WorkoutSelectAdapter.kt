package com.idan_koren_israeli.heartfit.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Equipment
import com.idan_koren_israeli.heartfit.model.Workout
import com.idan_koren_israeli.heartfit.recycler.holder.EquipmentSelectHolder
import com.idan_koren_israeli.heartfit.recycler.holder.WorkoutSelectHolder

class WorkoutSelectAdapter(context: Context, var workouts: ArrayList<ArrayList<Workout>>) :
    RecyclerView.Adapter<WorkoutSelectHolder>() {



    var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    private fun onWorkoutClick(workout: Workout){
        Log.i("pttt", workout.name + " Clicked!");
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutSelectHolder {
        val view: View = mInflater!!.inflate(R.layout.holder_workout_row,parent, false)

        return WorkoutSelectHolder(view) { workout -> onWorkoutClick(workout)}
    }

    override fun onBindViewHolder(holder: WorkoutSelectHolder, position: Int) {
            holder.setContent(workouts[position])
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

}
