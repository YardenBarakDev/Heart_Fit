package com.idan_koren_israeli.heartfit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hamzaahmedkhan.circulartimerview.CircularTimerListener
import com.hamzaahmedkhan.circulartimerview.CircularTimerView
import com.hamzaahmedkhan.circulartimerview.TimeFormatEnum
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Workout
import kotlin.math.ceil


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


    private lateinit var timerView : CircularTimerView
    private lateinit var pauseResumeButton : Button

    private var currentExercise:Int = 0

    private var paused : Boolean = false

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

        val parent:View = inflater.inflate(R.layout.fragment_workout, container, false)
        findViews(parent)
        initTimer()
        return parent
    }

    private fun findViews(parent: View){
        timerView = parent.findViewById(R.id.workout_CTV_timer)
        pauseResumeButton = parent.findViewById(R.id.workout_BTN_pause_resume)

    }

    private fun initTimer(){
        startTimerForNextExercise()
        pauseResumeButton.setOnClickListener { timerView.togglePauseResume() }

    }


    private fun startTimerForNextExercise(){
        if(currentExercise == workout.actions.size){
            workoutDone()
        }


        timerView.setCircularTimerListener(object : CircularTimerListener{
            override fun updateDataOnTick(remainingTimeInMs: Long): String {
                return ceil((remainingTimeInMs / 1000f).toDouble()).toInt().toString()
            }

            override fun onTimerFinished() {
                currentExercise++
                startTimerForNextExercise()

                // Next timer of next exercise will start
            }
        }, workout.actions[currentExercise].totalTime().toLong(), TimeFormatEnum.SECONDS,30)

        timerView.startTimer()

    }

    private fun workoutDone(){
        Log.i("pttt", "Finished")
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