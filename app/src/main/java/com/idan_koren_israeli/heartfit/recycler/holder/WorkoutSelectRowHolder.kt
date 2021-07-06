package com.idan_koren_israeli.heartfit.recycler.holder

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Workout


class WorkoutSelectRowHolder(itemView: View, private val onClick: (workout: Workout) -> Unit) : RecyclerView.ViewHolder(itemView){

    private lateinit var leftWorkout: FrameLayout
    private lateinit var centerWorkout: FrameLayout
    private lateinit var rightWorkout: FrameLayout

    init {
        findViews()
    }

    private fun findViews(){
        leftWorkout = itemView.findViewById(R.id.workouts_row_LAY_left)
        rightWorkout = itemView.findViewById(R.id.workouts_row_LAY_right)
        centerWorkout = itemView.findViewById(R.id.workouts_row_LAY_center)
    }

    fun setContent(workouts : ArrayList<Workout>){
        Log.i("pttt", "Settings " + workouts.size + " length")
        when(workouts.size){
            1 -> {
                leftWorkout.visibility = View.GONE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.GONE
                attachWorkoutToLayout(workouts[0],centerWorkout)
            }
            2 -> {
                leftWorkout.visibility = View.VISIBLE
                centerWorkout.visibility = View.GONE
                rightWorkout.visibility = View.VISIBLE

                attachWorkoutToLayout(workouts[0],leftWorkout)
                attachWorkoutToLayout(workouts[1],rightWorkout)
            }
            3 -> {
                leftWorkout.visibility = View.VISIBLE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.VISIBLE

                attachWorkoutToLayout(workouts[0],leftWorkout)
                attachWorkoutToLayout(workouts[1],centerWorkout)
                attachWorkoutToLayout(workouts[2],rightWorkout)
            }
        }
    }

    private fun attachWorkoutToLayout(workout: Workout, workoutHolder: FrameLayout) {
        val workoutName: TextView = workoutHolder.findViewById(R.id.workout_holder_LBL_name)
        val workoutImage: ImageView = workoutHolder.findViewById(R.id.workout_holder_IMG_image)

        workoutName.text = (workout.name)

        workoutHolder.setOnClickListener {
            run {
                Log.i("pttt","Workout Clicked: " + workout.name)

            }
        }
    }

}