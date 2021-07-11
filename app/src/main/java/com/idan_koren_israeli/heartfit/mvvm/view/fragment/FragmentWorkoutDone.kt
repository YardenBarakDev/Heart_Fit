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
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummaryDao
import com.idan_koren_israeli.heartfit.mvvm.model.WorkoutLog
import com.idan_koren_israeli.heartfit.mvvm.view_model.HistoryViewModel
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


        //TODO save workout summary to ROOMSQL
        // Id is currently 0, need to check for auto-generation
        val summary:WorkoutSummary = WorkoutSummary(id = 0, timestamp = System.currentTimeMillis(),
            userId = DatabaseManager.currentUser.uid!!, heartsCollected = workoutLog!!.workout!!.heartsValue,
            caloriesBurned = workoutLog!!.caloriesBurned!!, totalDurationSeconds = workoutLog!!.totalDuration,
            difficulty = workoutLog!!.workout!!.workoutLevel.name, muscles = workoutLog!!.workout!!.muscle.joinToString(separator = ", "))


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
        heartsText.text = workoutLog!!.workout!!.heartsValue.toString()



        val exercisesAdapter = ExercisesDoneAdapter(requireContext(), workoutLog!!.exercisesDone, DatabaseManager.currentUser.weight)
        exercisesRecycler.layoutManager = LinearLayoutManager(requireContext())

        exercisesRecycler.adapter = exercisesAdapter

    }

}