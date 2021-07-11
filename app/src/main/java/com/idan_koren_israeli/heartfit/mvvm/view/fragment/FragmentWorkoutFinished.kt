package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.mvvm.model.Exercise
import com.idan_koren_israeli.heartfit.mvvm.model.WorkoutLog
import com.idan_koren_israeli.heartfit.recycler.adapter.EquipmentSelectAdapter
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val parent = inflater.inflate(R.layout.fragment_workout_finished, container, false)
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



        val exercisesAdapter = ExercisesDoneAdapter(requireContext(), workoutLog!!.exercisesDone)
        exercisesRecycler.layoutManager = LinearLayoutManager(requireContext())

        exercisesRecycler.adapter = exercisesAdapter

    }

}