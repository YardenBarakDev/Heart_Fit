package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hamzaahmedkhan.circulartimerview.CircularTimerListener
import com.hamzaahmedkhan.circulartimerview.CircularTimerView
import com.hamzaahmedkhan.circulartimerview.TimeFormatEnum
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.mvvm.model.*
import com.idankorenisraeli.customprogressbar.CustomProgressBar
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil


/**
 *  This fragment will be shown when the user is doing the workout itself
 *  A timer will be shown on screen
 */


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val WORKOUT_KEY = "workout"
private const val EXERCISES_KEY = "exercises"
private const val USER_KEY = "user"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWorkout.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentWorkout : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var workout: Workout
    private lateinit var user: User
    private lateinit var exercises: List<Exercise>


    private lateinit var workoutProgress:CustomProgressBar
    private lateinit var nextExerciseText: TextView
    private lateinit var nextExerciseTitle: TextView
    private lateinit var timerView : CircularTimerView
    private lateinit var pauseResumeButton : ImageButton
    private lateinit var nextButton : ImageButton
    private lateinit var prevButton : ImageButton
    private lateinit var heartsCount: TextView
    private lateinit var durationTimerText: TextView

    private var totalWorkoutTime:Int = 0
    private var currentExercise:Int = 0



    private var paused : Boolean = false

    private lateinit  var workoutLog: WorkoutLog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workout = it.getSerializable(WORKOUT_KEY) as Workout
            exercises = it.getSerializable(EXERCISES_KEY) as ArrayList<Exercise>
            user = it.getSerializable(USER_KEY) as User

            workoutLog = WorkoutLog(workout, 0, arrayListOf(), 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val parent:View = inflater.inflate(R.layout.fragment_workout, container, false)
        findViews(parent)
        initTimers()
        initProgress()
        initButtons()

        for(exe:Exercise in exercises){
            Log.i("pttt", exe.name + " | " + exe.timeInSeconds)
        }

        return parent
    }

    private fun findViews(parent: View){
        timerView = parent.findViewById(R.id.workout_CTV_timer)
        pauseResumeButton = parent.findViewById(R.id.workout_BTN_pause_resume)
        nextButton = parent.findViewById(R.id.workout_BTN_next)
        prevButton = parent.findViewById(R.id.workout_BTN_prev)
        nextExerciseText = parent.findViewById(R.id.workout_LBL_next_exercise)
        nextExerciseTitle = parent.findViewById(R.id.workout_LBL_next_title)
        workoutProgress = parent.findViewById(R.id.workout_PRG_progress_bar)
        heartsCount = parent.findViewById(R.id.workout_LBL_heart_count)
        durationTimerText= parent.findViewById(R.id.workout_LBL_duration)

    }

    private fun initTimers(){
        startTimerForNextExercise()

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                if(!paused) {
                    workoutLog.totalDuration = workoutLog.totalDuration.plus(1)
                    updateDurationTimer(workoutLog.totalDuration)
                }
                mainHandler.postDelayed(this, 1000)
            }
        })


    }

    private fun initProgress(){
        totalWorkoutTime = exercises.fold(0,{acc, exercise ->  acc+exercise.timeInSeconds!!})
        workoutProgress.value = 0f

        workoutProgress.textTitle = capitalizeWords(workout.name!!)
        heartsCount.text = workout.heartsValue.toString()
    }


    private fun startTimerForNextExercise(){
        if(currentExercise == exercises.size){
            workoutDone()
            return
        }
        updateNextPrevButtonsVisibility()
        updateProgressBar()
        updateNextUpText()


        timerView.setCircularTimerListener(object : CircularTimerListener{
            override fun updateDataOnTick(remainingTimeInMs: Long): String {
                return generateTimeTextFromSeconds((remainingTimeInMs / 1000f).toInt())
            }

            override fun onTimerFinished() {
                // Currently we are tracking each exercise that is fully complete via timer
                // When user skips/goes back to other exercise it will not be saved
                workoutLog.trackExerciseDone(exercises[currentExercise], user.weight!!)
                currentExercise++
                startTimerForNextExercise()
                // Next timer of next exercise will start
            }
        }, exercises[currentExercise].timeInSeconds!!.toLong(), TimeFormatEnum.SECONDS,30)

        timerView.startTimer()
        timerView.prefix = capitalizeWords(exercises[currentExercise].name!!)

    }

    private fun generateTimeTextFromSeconds(numOfSeconds:Int):String{
        val minutes = numOfSeconds/60
        val seconds = ceil(((numOfSeconds%60)).toDouble()).toInt()
        val stb:StringBuilder = java.lang.StringBuilder()
        if(minutes<10)
            stb.append('0')
        stb.append(minutes)
        stb.append(':')
        if(seconds<10)
            stb.append('0')
        stb.append(seconds)
        return stb.toString()
    }

    private fun updateProgressBar() {
        workoutProgress.value = calculateProgressBarValue()

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

    private fun updateNextUpText(){
        if(currentExercise==exercises.size-1){
            nextExerciseText.visibility = View.INVISIBLE
            nextExerciseTitle.visibility = View.INVISIBLE
        }
        else{
            nextExerciseTitle.visibility = View.VISIBLE
            nextExerciseText.visibility = View.VISIBLE
            nextExerciseText.text = capitalizeWords(exercises[currentExercise+1].name!!)
        }


    }

    private fun capitalizeWords(orgText:String):String{
        return orgText.split(' ').joinToString(" ") { it ->
            it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            } }
    }


    private fun calculateProgressBarValue():Float{
        var timePastExercises:Int = 0
        exercises.forEachIndexed lit@{index, exercise ->
            if(index>currentExercise) return@lit
            timePastExercises+=exercise.timeInSeconds!!
        } // local return to the caller of the lambda

        return timePastExercises / totalWorkoutTime.toFloat()
    }

    private fun workoutDone(){
        /*
           pass data between fragments
           https://developer.android.com/guide/navigation/navigation-pass-data
           User has enough hearts*/
        /** TODO: I don't know what kind of data you transfer, please contact me if need assistant*/

        val bundle = bundleOf("workout_log" to workoutLog)

        //move to workoutFinished fragment
        findNavController().navigate(R.id.action_fragmentWorkout_to_fragmentWorkoutFinished, bundle)

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
        fun newInstance(workout: Workout, exercises:List<Exercise>, user:User) =
            FragmentWorkout().apply {
                arguments = Bundle().apply {
                    putSerializable(WORKOUT_KEY, workout)
                    putSerializable(EXERCISES_KEY, exercises as Serializable)
                    putSerializable(USER_KEY, user)
                }
            }
    }

    private fun updateDurationTimer(seconds:Int) {
        durationTimerText.text=
            String.format("%02d:%02d:%02d", seconds / 3600,
                (seconds % 3600) / 60, (seconds % 60));
    }

}