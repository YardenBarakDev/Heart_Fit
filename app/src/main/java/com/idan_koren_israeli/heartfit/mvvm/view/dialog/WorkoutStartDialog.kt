package com.idan_koren_israeli.heartfit.mvvm.view.dialog

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.firebase.firestore.FirestoreManager
import com.idan_koren_israeli.heartfit.mvvm.model.Exercise
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
import com.idan_koren_israeli.heartfit.mvvm.model.Workout
import org.jetbrains.anko.margin
import java.util.concurrent.TimeUnit

class WorkoutStartDialog(val activity: Activity) {
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var parentLayout: ViewGroup
    private lateinit var dialogLayout: ViewGroup

    fun create() {
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(activity)
    }


    fun inflate() {
        dialogLayout = (LayoutInflater.from(activity)
            .inflate(R.layout.dialog_workout_start, null, false) as ViewGroup)

        parentLayout = dialogLayout.findViewById(R.id.workout_start_dialog_LAY_parent)
    }

    private fun addEquipmentToLayout(allEquipment: Collection<Equipment?>) {
        for (equipment in allEquipment) {
            val equipmentHolder = LayoutInflater.from(activity)
                .inflate(R.layout.holder_equipment_line, parentLayout, false)


            val equipmentText : TextView = equipmentHolder.findViewById(R.id.equipment_line_LBL_text)
            val equipmentImage : ImageView = equipmentHolder.findViewById(R.id.equipment_line_IMG_image)


            equipmentText.text = equipment!!.displayName
            equipmentImage.setImageDrawable(ContextCompat.getDrawable(activity, equipment.imageId))


            val layoutParams: ViewGroup.MarginLayoutParams =
                ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            layoutParams.setMargins(15,5,15,5)

            equipmentHolder.layoutParams = layoutParams

            parentLayout.addView(equipmentHolder)
        }


    }

    fun launch(workout: Workout, exercises: List<Exercise>) {
        
        val neededEquipment = mutableSetOf<Equipment>()

        val totalWorkoutTime = exercises.fold(0, {acc, exercise -> acc + exercise.timeInSeconds!! })

        for(exercise in exercises){
            if(exercise.equipment!=null)
                neededEquipment.addAll(exercise.equipment)
        }
        
        
        // Building the Alert dialog using materialAlertDialogBuilder instance
        if(neededEquipment.size > 0)
            addEquipmentToLayout(neededEquipment)


        val firstLineMsg = "Gain " + workout.heartsValue + " hearts by finishing this workout."

        val secondLineMsg = when(totalWorkoutTime%60 == 0){
            true->String.format("It will take %d minutes.",
                totalWorkoutTime / 60)
            false ->String.format("It will take %d minutes and %d seconds.",
                totalWorkoutTime / 60 , totalWorkoutTime % 60)
        }

        val thirdLineMsg = when (neededEquipment.size) {
            0 -> "No equipment is needed."
            else -> "Equipment you will need:"
        }



        materialAlertDialogBuilder.setView(dialogLayout)
            .setTitle("Start " + workout.name)
            .setMessage(
                firstLineMsg + "\n" + secondLineMsg  + "\n\n"+
                        thirdLineMsg
            )
            .setPositiveButton("Start") { dialog, _ ->


                val bundle = bundleOf(
                    "workout" to workout,
                    "exercises" to exercises,
                    "user" to DatabaseManager.currentUser
                )

                activity.findNavController(R.id.mainActivity_fragment)
                    .navigate(R.id.action_fragmentHome_to_fragmentWorkout, bundle)



                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun findViews() {

    }

}