package com.idan_koren_israeli.heartfit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Workout
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
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.home_FRAG_top_bar, topBar).commit()
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
        levelA.add(Workout(1,"Workouts A",heartsValue = 1, heartsToUnlock = 1))
        levelB.add(Workout(2,"Workouts B1",heartsValue = 1, heartsToUnlock = 1))
        levelB.add(Workout(3,"Workouts B2",heartsValue = 1, heartsToUnlock = 1))
        levelC.add(Workout(4,"Workouts C1",heartsValue = 1, heartsToUnlock = 1))
        levelC.add(Workout(5,"Workouts C2",heartsValue = 1, heartsToUnlock = 1))
        levelC.add(Workout(6,"Workouts C3",heartsValue = 1, heartsToUnlock = 1))
        levelD.add(Workout(7,"Workouts D1",heartsValue = 1, heartsToUnlock = 1))
        levelE.add(Workout(8,"Workouts E1",heartsValue = 1, heartsToUnlock = 1))
        levelE.add(Workout(9,"Workouts E2",heartsValue = 1, heartsToUnlock = 1))

        val workouts : ArrayList<ArrayList<Workout>> = arrayListOf()
        workouts.add(levelA)
        workouts.add(levelC)
        workouts.add(levelB)
        workouts.add(levelA)
        workouts.add(levelD)
        workouts.add(levelE)
        workouts.add(levelB)
        workouts.add(levelD)
        workouts.add(levelC)
        workouts.add(levelE)
        workouts.add(levelA)
        workouts.add(levelC)
        workouts.add(levelB)
        workouts.add(levelA)
        workouts.add(levelD)
        workouts.add(levelE)
        workouts.add(levelB)
        workouts.add(levelD)
        workouts.add(levelC)
        workouts.add(levelE)


        workoutsAdapter = WorkoutSelectAdapter(requireContext(), workouts)

        workoutsRecycler.adapter = workoutsAdapter


        val layoutManager = GridLayoutManager(requireContext(), 1)

        workoutsRecycler.layoutManager = layoutManager
    }

}