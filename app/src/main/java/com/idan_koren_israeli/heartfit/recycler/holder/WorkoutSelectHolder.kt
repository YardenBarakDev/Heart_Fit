package com.idan_koren_israeli.heartfit.recycler.holder

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Workout


class WorkoutSelectHolder(itemView: View, private val onClick: (workout: Workout) -> Unit) : RecyclerView.ViewHolder(itemView){

    private lateinit var leftWorkout: ImageButton
    private lateinit var centerWorkout: ImageButton
    private lateinit var rightWorkout: ImageButton

    init {
        findViews()
    }

    private fun findViews(){
        leftWorkout = itemView.findViewById(R.id.workouts_row_BTN_left)
        rightWorkout = itemView.findViewById(R.id.workouts_row_BTN_right)
        centerWorkout = itemView.findViewById(R.id.workouts_row_BTN_center)
    }

    fun setContent(workouts : ArrayList<Workout>){
        Log.i("pttt", "Settings " + workouts.size + " length")
        when(workouts.size){
            1 -> {
                leftWorkout.visibility = View.GONE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.GONE
                attachWorkoutToButton(workouts[0],centerWorkout)
            }
            2 -> {
                leftWorkout.visibility = View.VISIBLE
                centerWorkout.visibility = View.GONE
                rightWorkout.visibility = View.VISIBLE

                attachWorkoutToButton(workouts[0],leftWorkout)
                attachWorkoutToButton(workouts[1],rightWorkout)
            }
            3 -> {
                leftWorkout.visibility = View.VISIBLE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.VISIBLE

                attachWorkoutToButton(workouts[0],leftWorkout)
                attachWorkoutToButton(workouts[1],centerWorkout)
                attachWorkoutToButton(workouts[2],rightWorkout)
            }
        }
    }

    private fun attachWorkoutToButton(workout: Workout, button: ImageButton) {
        button.setOnClickListener {
            run {
                Log.i("pttt","Workout Clicked: " + workout.name)
            }
        }
    }

}