package com.idan_koren_israeli.heartfit.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.mvvm.model.Workout
import com.idan_koren_israeli.heartfit.recycler.holder.WorkoutSelectRowHolder

class WorkoutSelectAdapter(context: Context, var workouts: MutableList<ArrayList<Workout>>) :
    RecyclerView.Adapter<WorkoutSelectRowHolder>() {

    companion object{
        val workoutColors = listOf<Int>(
            R.color.workout_btn_background_1,
            R.color.workout_btn_background_2,
            R.color.workout_btn_background_3,
            R.color.workout_btn_background_4,
        )
    }

    var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    private fun onWorkoutClick(workout: Workout){
        Log.i("pttt", workout.name + " Clicked!")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutSelectRowHolder {
        val view: View = mInflater!!.inflate(R.layout.holder_workout_row,parent, false)

        return WorkoutSelectRowHolder(view) { workout -> onWorkoutClick(workout)}
    }

    override fun onBindViewHolder(rowHolder: WorkoutSelectRowHolder, position: Int) {
            rowHolder.setContent(workouts[position], workoutColors[position%workoutColors.size])
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

}
