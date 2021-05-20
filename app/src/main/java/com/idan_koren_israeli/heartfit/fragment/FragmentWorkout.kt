package com.idan_koren_israeli.heartfit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Workout


/**
 *  This fragment will be shown when the user is doing the workout itself
 *  A timer will be shown on screen
 */


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val WORKOUT_KEY = "workout"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWorkout.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentWorkout : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var workout: Workout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workout = it.getSerializable(WORKOUT_KEY) as Workout
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FragmentWorkout.
         */
        @JvmStatic
        fun newInstance(workout: Workout) =
            FragmentWorkout().apply {
                arguments = Bundle().apply {
                    putSerializable(WORKOUT_KEY, workout)
                }
            }
    }
}