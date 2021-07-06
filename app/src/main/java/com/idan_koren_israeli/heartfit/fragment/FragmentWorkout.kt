package com.idan_koren_israeli.heartfit.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.hamzaahmedkhan.circulartimerview.CircularTimerListener
import com.hamzaahmedkhan.circulartimerview.CircularTimerView
import com.hamzaahmedkhan.circulartimerview.TimeFormatEnum
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Exercise
import com.idan_koren_israeli.heartfit.model.Workout
import java.io.Serializable
import kotlin.math.ceil


/**
 *  This fragment will be shown when the user is doing the workout itself
 *  A timer will be shown on screen
 */


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val WORKOUT_KEY = "workout"
private const val EXERCISES_KEY = "exercises"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWorkout.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentWorkout : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var workout: Workout
    private lateinit var exercises: List<Exercise>


    private lateinit var timerView : CircularTimerView
    private lateinit var pauseResumeButton : ImageButton
    private lateinit var nextButton : ImageButton
    private lateinit var prevButton : ImageButton

    private var currentExercise:Int = 0

    private var paused : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workout = it.getSerializable(WORKOUT_KEY) as Workout
            exercises = it.getSerializable(EXERCISES_KEY) as ArrayList<Exercise>
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
        initButtons()

        for(exe:Exercise in exercises){
            Log.i("pttt", exe.name + " | " + exe.timeInSeconds)
        }

        return parent
    }

    private fun findViews(parent: View){
        timerView = parent.findViewById(R.id.workout_CTV_timer)
        pauseResumeButton = parent.findViewById(R.id.workout_BTN_pause_resume)
        nextButton = parent.findViewById(R.id.workout_BTN_next);
        prevButton = parent.findViewById(R.id.workout_BTN_prev);

    }

    private fun initTimer(){
        startTimerForNextExercise()

    }


    private fun startTimerForNextExercise(){
        updateNextPrevButtonsVisibility()
        if(currentExercise == exercises.size){
            workoutDone()
            return
        }


        timerView.setCircularTimerListener(object : CircularTimerListener{
            override fun updateDataOnTick(remainingTimeInMs: Long): String {
                return  " " + ceil((remainingTimeInMs / 1000f).toDouble()).toInt().toString()
            }

            override fun onTimerFinished() {
                currentExercise++
                startTimerForNextExercise()


                // Next timer of next exercise will start
            }
        }, exercises[currentExercise].timeInSeconds!!.toLong(), TimeFormatEnum.SECONDS,30)

        timerView.startTimer()
        timerView.prefix = exercises[currentExercise].name

    }

    private fun initButtons(){
        pauseResumeButton.setOnClickListener {
            timerView.togglePauseResume()
            paused = !paused

            if(paused)
                pauseResumeButton.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
            else
                pauseResumeButton.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
        }
        nextButton.setOnClickListener {
            currentExercise++
            startTimerForNextExercise()
        }
        prevButton.setOnClickListener {
            currentExercise--
            startTimerForNextExercise()
        }
    }

    private fun updateNextPrevButtonsVisibility(){
        if(currentExercise==0)
            prevButton.visibility = View.INVISIBLE
        else
            prevButton.visibility = View.VISIBLE

        if(currentExercise==exercises.size - 1)
            nextButton.visibility = View.INVISIBLE
        else
            nextButton.visibility = View.VISIBLE

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
        fun newInstance(workout: Workout, exercises:List<Exercise>) =
            FragmentWorkout().apply {
                arguments = Bundle().apply {
                    putSerializable(WORKOUT_KEY, workout)
                    putSerializable(EXERCISES_KEY, exercises as Serializable)
                }
            }
    }
}