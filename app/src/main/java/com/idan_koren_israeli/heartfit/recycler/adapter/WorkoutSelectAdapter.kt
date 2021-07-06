package com.idan_koren_israeli.heartfit.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Workout
import com.idan_koren_israeli.heartfit.recycler.holder.WorkoutSelectRowHolder

class WorkoutSelectAdapter(context: Context, var workouts: ArrayList<ArrayList<Workout>>) :
    RecyclerView.Adapter<WorkoutSelectRowHolder>() {



    var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    private fun onWorkoutClick(workout: Workout){
        Log.i("pttt", workout.name + " Clicked!");
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutSelectRowHolder {
        val view: View = mInflater!!.inflate(R.layout.holder_workout_row,parent, false)

        return WorkoutSelectRowHolder(view) { workout -> onWorkoutClick(workout)}
    }

    override fun onBindViewHolder(rowHolder: WorkoutSelectRowHolder, position: Int) {
            rowHolder.setContent(workouts[position])
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

}
