package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummaryDao
import com.idan_koren_israeli.heartfit.mvvm.model.WorkoutLog
import com.idan_koren_israeli.heartfit.mvvm.view_model.HistoryViewModel
import com.idan_koren_israeli.heartfit.mvvm.view_model.WorkoutFinishedViewModel
import com.idan_koren_israeli.heartfit.recycler.adapter.ExercisesDoneAdapter


private const val KEY_WORKOUT_LOG = "workout_log"

class FragmentWorkoutFinished : Fragment() {

    private var workoutLog: WorkoutLog? = null

    private lateinit var titleText:TextView
    private lateinit var caloriesText:TextView
    private lateinit var durationText:TextView
    private lateinit var exercisesCountText:TextView
    private lateinit var heartsText:TextView
    private lateinit var exercisesRecycler:RecyclerView
    private lateinit var doneActionButton:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workoutLog = it.getSerializable(KEY_WORKOUT_LOG) as WorkoutLog
        }


        val heartsCollected = when(isRealDone()){
            true-> workoutLog!!.workout!!.heartsValue
            false -> 0
        }

        val summary:WorkoutSummary = WorkoutSummary(id = 0, timestamp = System.currentTimeMillis(),
            userId = DatabaseManager.currentUser.uid!!, heartsCollected = heartsCollected,
            caloriesBurned = workoutLog!!.caloriesBurned!!, totalDurationSeconds = workoutLog!!.totalDuration,
            difficulty = workoutLog!!.workout!!.workoutLevel.name, muscles = workoutLog!!.workout!!.muscle.joinToString(separator = ", "),
            name = workoutLog!!.workout!!.name!!)

        WorkoutFinishedViewModel.storeWorkoutInDB(summary)

        if(heartsCollected!=0){
           DatabaseManager.currentUser.hearts = DatabaseManager.currentUser.hearts.plus(workoutLog!!.workout!!.heartsValue)
           DatabaseManager.storeCurrentUser()
        }

    }

    // Checks if workout was long enough to be considered as done
    // because user can skip exercises
    private fun isRealDone(): Boolean {
        return (workoutLog!!.exercisesDone.size + 3 >= workoutLog!!.totalExercisesCount)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val parent = inflater.inflate(R.layout.fragment_workout_done, container, false)
        findViews(parent)
        attachInfo()
        initDoneButton()
        return parent
    }

    private fun initDoneButton(){
        doneActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentWorkoutFinished_to_fragmentHome)

        }
    }

    private fun findViews(parent: View){
        titleText = parent.findViewById(R.id.workout_finish_LBL_title)
        caloriesText = parent.findViewById(R.id.workout_finish_LBL_calories)
        exercisesCountText = parent.findViewById(R.id.workout_finish_LBL_exercises_count)
        heartsText = parent.findViewById(R.id.workout_finish_LBL_hearts)
        durationText = parent.findViewById(R.id.workout_finish_LBL_duration)
        exercisesRecycler = parent.findViewById(R.id.workout_finish_RYC_exercises)
        doneActionButton = parent.findViewById(R.id.workout_finish_FAB_done)
    }

    private fun attachInfo(){
        val seconds : Int = workoutLog!!.totalDuration

        titleText.text = workoutLog!!.workout!!.name
        caloriesText.text = workoutLog!!.caloriesBurned.toString()
        durationText.text = String.format("%02d:%02d:%02d", seconds / 3600,
            (seconds % 3600) / 60, (seconds % 60));
        exercisesCountText.text = workoutLog!!.exercisesDone.size.toString()

        if(isRealDone())
            heartsText.text = workoutLog!!.workout!!.heartsValue.toString()
        else {
            heartsText.text = "0"
            CommonUtils.getInstance().showToast("Too many exercises skipped\nno hearts collected.")
        }


        val exercisesAdapter = ExercisesDoneAdapter(requireContext(), workoutLog!!.exercisesDone, DatabaseManager.currentUser.weight)
        exercisesRecycler.layoutManager = LinearLayoutManager(requireContext())

        exercisesRecycler.adapter = exercisesAdapter

    }

}