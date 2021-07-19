package com.idan_koren_israeli.heartfit.recycler.holder

import android.app.Activity
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
import com.idan_koren_israeli.heartfit.component.WorkoutGenerator
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.firebase.firestore.FirestoreManager
import com.idan_koren_israeli.heartfit.mvvm.model.*
import com.idan_koren_israeli.heartfit.mvvm.view.dialog.WorkoutStartDialogManager


class WorkoutSelectRowHolder(
    itemView: View,
    private val currentUser: User,
    private val onClick: (workout: Workout) -> Unit
) :
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

    fun setContent(workouts: ArrayList<Workout>, colorId: Int) {
        when (workouts.size) {
            1 -> {
                leftWorkout.visibility = View.GONE
                centerWorkout.visibility = View.VISIBLE
                rightWorkout.visibility = View.GONE
                attachWorkoutToLayout(workouts[0], centerWorkout, colorId)
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

    private fun attachWorkoutToLayout(
        workout: Workout,
        workoutHolder: ViewGroup,
        colorId: Int
    ) {
        val clickable: Boolean = (currentUser.hearts >= workout.heartsToUnlock)


        val workoutName: TextView = workoutHolder.findViewById(R.id.workout_holder_LBL_name)
        val workoutImage: ImageView = workoutHolder.findViewById(R.id.workout_holder_IMG_image)
        val heartImage: ImageView = workoutHolder.findViewById(R.id.workout_holder_IMG_heart)
        val heartsToUnlockText: TextView =
            workoutHolder.findViewById(R.id.workout_holder_LBL_hearts)

        if (!clickable)
            heartImage.setImageResource(R.drawable.ic_heart_outlined_locked)
        else
            heartImage.setImageResource(R.drawable.ic_heart_outlined)


        setWorkoutButtonBackgroundColor(workoutImage, colorId)

        workoutName.text = (workout.name)
        heartsToUnlockText.text = workout.heartsToUnlock.toString()

        workoutHolder.setOnClickListener {
            onClick.invoke(workout)
        }
    }


    private fun setWorkoutButtonBackgroundColor(workoutImage: ImageView, colorId: Int) {
        val layerDrawable = workoutImage.background as LayerDrawable
        val iconBackgroundDrawable =
            layerDrawable.findDrawableByLayerId(R.id.ic_workout_ITEM_background) as GradientDrawable
        iconBackgroundDrawable.setColor(ContextCompat.getColor(workoutImage.context, colorId))

    }

}