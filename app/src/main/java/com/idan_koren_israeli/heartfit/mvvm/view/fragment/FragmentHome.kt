package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.component.WorkoutGenerator
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
import com.idan_koren_israeli.heartfit.mvvm.model.MuscleGroup
import com.idan_koren_israeli.heartfit.mvvm.model.Workout
import com.idan_koren_israeli.heartfit.mvvm.model.WorkoutLevel
import com.idan_koren_israeli.heartfit.mvvm.repository.WorkoutRepository
import com.idan_koren_israeli.heartfit.mvvm.view.dialog.WorkoutStartDialogManager
import com.idan_koren_israeli.heartfit.recycler.adapter.WorkoutSelectAdapter
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment() {

    private lateinit var workoutsRecycler: RecyclerView
    private lateinit var workoutsAdapter: WorkoutSelectAdapter
    private lateinit var viewFragmentHome: View
    private lateinit var topBar: FragmentTopBar
    private lateinit var loadingScreen: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun initTopBarFragment() {
        topBar = FragmentTopBar.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.home_FRAG_top_bar, topBar).commitAllowingStateLoss()
    }

    private fun findViews() {
        workoutsRecycler = viewFragmentHome.findViewById(R.id.home_RYC_workouts)
        loadingScreen = viewFragmentHome.findViewById(R.id.home_LAY_loading)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFragmentHome = inflater.inflate(R.layout.fragment_home, container, false)
        findViews()
        initTopBarFragment()
        return viewFragmentHome
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

        workoutsRecycler.adapter = workoutsAdapter

        val layoutManager = GridLayoutManager(requireContext(), 1)

        workoutsRecycler.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()
        initRecycler()
    }


    private fun showLoading() {
        loadingScreen.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingScreen.visibility = View.GONE
    }


}