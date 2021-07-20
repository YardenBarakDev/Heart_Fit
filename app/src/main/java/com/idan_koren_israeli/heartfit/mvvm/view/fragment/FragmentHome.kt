package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.component.WorkoutGenerator
import com.idan_koren_israeli.heartfit.databinding.FragmentHomeBinding
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.mvvm.repository.WorkoutRepository
import com.idan_koren_israeli.heartfit.mvvm.view.dialog.WorkoutStartDialogManager
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
                    .showToast("Not enough ❤ to start this workout")
            } else {
                // User has enough hearts, workout can be started
                // Generating workout by loading exercises from firebase
                showLoading()

                WorkoutGenerator.generateWorkout(workout) { exercises ->

                    val dialogManager = WorkoutStartDialogManager(requireActivity())

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
    }


    private fun showLoading() {
        binding.homeRYCWorkouts.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.homeRYCWorkouts.visibility = View.GONE
    }


}