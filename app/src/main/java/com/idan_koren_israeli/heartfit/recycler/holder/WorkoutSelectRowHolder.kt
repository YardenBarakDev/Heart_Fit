package com.idan_koren_israeli.heartfit.recycler.holder

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.firebase.firestore.FirestoreManager
import com.idan_koren_israeli.heartfit.mvvm.model.*
import java.io.Serializable


class WorkoutSelectRowHolder(itemView: View, private val onClick: (workout: Workout) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private lateinit var leftWorkout: ViewGroup
    private lateinit var centerWorkout: ViewGroup
    private lateinit var rightWorkout: ViewGroup

    init {
        findViews()
    }

    private fun findViews() {
        leftWorkout = itemView.findViewById(R.id.workouts_row_LAY_left)
        rightWorkout = itemView.findViewById(R.id.workouts_row_LAY_right)
        centerWorkout = itemView.findViewById(R.id.workouts_row_LAY_center)
    }

    fun setContent(workouts: ArrayList<Workout>) {
        Log.i("pttt", "Settings " + workouts.size + " length")
        when (workouts.size) {
            1 -> {
                leftWorkout.visibility = View.GONE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.GONE
                attachWorkoutToLayout(workouts[0], centerWorkout)
            }
            2 -> {
                leftWorkout.visibility = View.VISIBLE
                centerWorkout.visibility = View.GONE
                rightWorkout.visibility = View.VISIBLE

                attachWorkoutToLayout(workouts[0], leftWorkout)
                attachWorkoutToLayout(workouts[1], rightWorkout)
            }
            3 -> {
                leftWorkout.visibility = View.VISIBLE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.VISIBLE

                attachWorkoutToLayout(workouts[0], leftWorkout)
                attachWorkoutToLayout(workouts[1], centerWorkout)
                attachWorkoutToLayout(workouts[2], rightWorkout)
            }
        }
    }

    private fun attachWorkoutToLayout(workout: Workout, workoutHolder: ViewGroup) {
        val workoutName: TextView = workoutHolder.findViewById(R.id.workout_holder_LBL_name)
        val workoutImage: ImageView = workoutHolder.findViewById(R.id.workout_holder_IMG_image)
        val heartsToUnlockText: TextView =
            workoutHolder.findViewById(R.id.workout_holder_LBL_hearts)

        workoutName.text = (workout.name)
        heartsToUnlockText.text = workout.heartsToUnlock.toString()

        workoutHolder.setOnClickListener {
            run {
                if (DatabaseManager.currentUser.hearts < workout.heartsToUnlock) {
                    CommonUtils.getInstance()
                        .showToast("Not enough hearts to start this workout")
                } else {
                    // User has enough hearts, workout can be started
                    /*
                    pass data between fragments
                    https://developer.android.com/guide/navigation/navigation-pass-data*/

                    FirestoreManager.loadExercisesByName("Bench press") {
                        val bundle = bundleOf(
                            "workout" to workout,
                            "exercises" to it,
                            "user" to DatabaseManager.currentUser
                        )
                        itemView.findNavController()
                            .navigate(R.id.action_fragmentHome_to_fragmentWorkout, bundle)
                    }
                }
            }
        }
    }

}