package com.idan_koren_israeli.heartfit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Equipment
import com.idan_koren_israeli.heartfit.model.MuscleGroup
import com.idan_koren_israeli.heartfit.model.Workout
import com.idan_koren_israeli.heartfit.model.WorkoutLevel
import com.idan_koren_israeli.heartfit.recycler.adapter.WorkoutSelectAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment() {

    private lateinit var workoutsRecycler : RecyclerView
    private lateinit var workoutsAdapter: WorkoutSelectAdapter

    private lateinit var topBar: FragmentTopBar

    private companion object{

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHome().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun initTopBarFragment(){
        topBar = FragmentTopBar.newInstance()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.home_FRAG_top_bar, topBar).commitAllowingStateLoss()
    }

    private fun findViews(parent: View){
        workoutsRecycler = parent.findViewById(R.id.home_RYC_workouts)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
            ): View? {
        // Inflate the layout for this fragment
        val parent = inflater.inflate(R.layout.fragment_home, container, false)
        findViews(parent)
        initTopBarFragment()
        initRecycler()
        return parent
    }


    private fun initRecycler(){
        val levelA = arrayListOf<Workout>()
        val levelB = arrayListOf<Workout>()
        val levelC = arrayListOf<Workout>()
        val levelD = arrayListOf<Workout>()
        val levelE = arrayListOf<Workout>()
        levelA.add(Workout("Workout 1", listOf(Equipment.Bench), listOf(MuscleGroup.ABDOMINALS),4,0,WorkoutLevel.Basic))
        levelB.add(Workout("Workout 2", listOf(Equipment.Bench), listOf(MuscleGroup.ABDOMINALS),4,3,WorkoutLevel.Basic))
        levelB.add(Workout("Workout 3", listOf(Equipment.Bench), listOf(MuscleGroup.ABDOMINALS),4,7,WorkoutLevel.Basic))
        levelC.add(Workout("Workout 5", listOf(Equipment.Bench), listOf(MuscleGroup.ABDOMINALS),4,8,WorkoutLevel.Basic))
        levelD.add(Workout("Workout 6", listOf(Equipment.Bench), listOf(MuscleGroup.ABDOMINALS),4,12,WorkoutLevel.Basic))
        levelD.add(Workout("Workout 7", listOf(Equipment.Bench), listOf(MuscleGroup.ABDOMINALS),4,12,WorkoutLevel.Basic))
        levelE.add(Workout("Workout 8", listOf(Equipment.Bench), listOf(MuscleGroup.ABDOMINALS),4,15,WorkoutLevel.Basic))


        val workouts : ArrayList<ArrayList<Workout>> = arrayListOf()
        workouts.add(levelA)
        workouts.add(levelB)
        workouts.add(levelC)
        workouts.add(levelD)
        workouts.add(levelE)


        workoutsAdapter = WorkoutSelectAdapter(requireContext(), workouts)

        workoutsRecycler.adapter = workoutsAdapter


        val layoutManager = GridLayoutManager(requireContext(), 1)

        workoutsRecycler.layoutManager = layoutManager
    }

}