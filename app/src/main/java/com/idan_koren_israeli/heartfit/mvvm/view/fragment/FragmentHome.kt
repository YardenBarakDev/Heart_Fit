package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.component.WorkoutGenerator
import com.idan_koren_israeli.heartfit.databinding.FragmentHomeBinding
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import com.idan_koren_israeli.heartfit.mvvm.model.MuscleGroup
import com.idan_koren_israeli.heartfit.mvvm.model.WorkoutLevel
import com.idan_koren_israeli.heartfit.mvvm.repository.WorkoutRepository
import com.idan_koren_israeli.heartfit.mvvm.view.dialog.WorkoutStartDialog
import com.idan_koren_israeli.heartfit.mvvm.view_model.WorkoutFinishedViewModel
import com.idan_koren_israeli.heartfit.recycler.adapter.WorkoutSelectAdapter

class FragmentHome : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var workoutsAdapter: WorkoutSelectAdapter
    private lateinit var topBar: FragmentTopBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initTopBarFragment()
    }
    private fun initTopBarFragment() {
        topBar = FragmentTopBar()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.home_FRAG_top_bar, topBar).commitAllowingStateLoss()
    }

    private fun initRecycler() {

        workoutsAdapter = WorkoutSelectAdapter(
            requireContext(),
            WorkoutRepository.getWorkoutsTree(),
            DatabaseManager.currentUser
        ) { workout ->
            if (DatabaseManager.currentUser.hearts < workout.heartsToUnlock) {
                CommonUtils.getInstance()
                    .showToast("Not enough ??? to start this workout")
            } else {
                // User has enough hearts, workout can be started
                // Generating workout by loading exercises from firebase
                showLoading()

                WorkoutGenerator.generateWorkout(workout) { exercises ->

                    val dialogManager = WorkoutStartDialog(requireActivity())

                    dialogManager.create()
                    dialogManager.inflate()

                    hideLoading()
                    dialogManager.launch(workout, exercises)

                }
            }
        }

        binding.homeRYCWorkouts.adapter = workoutsAdapter

        val layoutManager = GridLayoutManager(requireContext(), 1)

        binding.homeRYCWorkouts.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()
        initRecycler()

        //storeExampleWorkout()

    }


    // For debugging usage
    private fun storeExampleWorkout(){

        Log.i("pttt", "Storing Workout ")
        val summary = WorkoutSummary(id = 0, timestamp = 1626700945000,
            userId = DatabaseManager.currentUser.uid!!, heartsCollected = 5,
            caloriesBurned = 84, totalDurationSeconds = 512,
            difficulty = WorkoutLevel.Basic.name, muscles = listOf(MuscleGroup.LEGS).joinToString(separator = ", "),
            name = "Legs 1")

        WorkoutFinishedViewModel.storeWorkoutInDB(summary)

    }


    private fun showLoading() {
        binding.homeLAYLoading.root.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.homeLAYLoading.root.visibility = View.GONE
    }


}