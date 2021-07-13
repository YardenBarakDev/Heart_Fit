package com.idan_koren_israeli.heartfit.recycler.holder

import android.graphics.Color
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.firebase.firestore.FirestoreManager
import com.idan_koren_israeli.heartfit.mvvm.model.*


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

    fun setContent(workouts: ArrayList<Workout>, colorId:Int) {
        Log.i("pttt", "Settings " + workouts.size + " length")
        when (workouts.size) {
            1 -> {
                leftWorkout.visibility = View.GONE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.GONE
                attachWorkoutToLayout(workouts[0], centerWorkout,colorId)
            }
            2 -> {
                leftWorkout.visibility = View.VISIBLE
                centerWorkout.visibility = View.GONE
                rightWorkout.visibility = View.VISIBLE

                attachWorkoutToLayout(workouts[0], leftWorkout, colorId)
                attachWorkoutToLayout(workouts[1], rightWorkout, colorId)
            }
            3 -> {
                leftWorkout.visibility = View.VISIBLE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.VISIBLE

                attachWorkoutToLayout(workouts[0], leftWorkout, colorId)
                attachWorkoutToLayout(workouts[1], centerWorkout, colorId)
                attachWorkoutToLayout(workouts[2], rightWorkout, colorId)
            }
        }
    }

    private fun attachWorkoutToLayout(workout: Workout, workoutHolder: ViewGroup, colorId: Int) {
        val workoutName: TextView = workoutHolder.findViewById(R.id.workout_holder_LBL_name)
        val workoutImage: ImageView = workoutHolder.findViewById(R.id.workout_holder_IMG_image)
        val heartsToUnlockText: TextView =
            workoutHolder.findViewById(R.id.workout_holder_LBL_hearts)


        setWorkoutButtonBackgroundColor(workoutImage, colorId)

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


    private fun setWorkoutButtonBackgroundColor(workoutImage: ImageView, colorId:Int){
        val layerDrawable = workoutImage.background as LayerDrawable
        val iconBackgroundDrawable = layerDrawable.findDrawableByLayerId(R.id.ic_workout_ITEM_background) as GradientDrawable
        iconBackgroundDrawable.setColor(ContextCompat.getColor(workoutImage.context, colorId))

    }

}