package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamzaahmedkhan.circulartimerview.CircularTimerListener
import com.hamzaahmedkhan.circulartimerview.CircularTimerView
import com.hamzaahmedkhan.circulartimerview.TimeFormatEnum
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.mvvm.model.*
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
import com.idankorenisraeli.customprogressbar.CustomProgressBar
import com.idankorenisraeli.mysettingsscreen.MySettingsScreen
import com.idankorenisraeli.mysettingsscreen.tile_data.view.SeekbarTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.view.ToggleTileData
import java.io.Serializable
import java.util.*
import kotlin.math.ceil


/**
 *  This fragment will be shown when the user is doing the workout itself
 *  A timer will be shown on screen
 */


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val WORKOUT_KEY = "workout"
private const val EXERCISES_KEY = "exercises"
private const val USER_KEY = "user"

private const val ANIMATION_RESOURCE_NAME_PREFIX = "anim_exercise_"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWorkout.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentWorkout : Fragment(), TextToSpeech.OnInitListener {
    private lateinit var workout: Workout
    private lateinit var user: User
    private lateinit var exercises: ArrayList<Exercise>


    private lateinit var workoutProgress:CustomProgressBar
    private lateinit var animationImage: ImageView
    private lateinit var nextExerciseText: TextView
    private lateinit var nextExerciseTitle: TextView
    private lateinit var timerView : CircularTimerView
    private lateinit var pauseResumeButton : ImageButton
    private lateinit var nextButton : ImageButton
    private lateinit var prevButton : ImageButton
    private lateinit var heartsCount: TextView
    private lateinit var durationTimerText: TextView
    private lateinit var equipmentText: TextView
    private var prepTimeSeconds: Int = 0


    private var totalWorkoutTime:Int = 0
    private var currentExercise:Int = 0

    private lateinit var tts : TextToSpeech
    private var ttsEnabled : Boolean = false

    private var paused : Boolean = false

    private lateinit  var workoutLog: WorkoutLog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workout = it.getSerializable(WORKOUT_KEY) as Workout
            exercises = it.getSerializable(EXERCISES_KEY) as ArrayList<Exercise>
            user = it.getSerializable(USER_KEY) as User

            workoutLog = WorkoutLog(workout, 0, arrayListOf(), 0, totalExercisesCount = exercises.size)
        }

        tts = TextToSpeech(requireContext(), this)

        initBackPress()
    }

    private fun initBackPress() {

        // This callback will only be called when MyFragment is at least Started.

        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    pause()
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.workout_exit_title)
                        .setMessage(R.string.workout_exit_message)
                        .setPositiveButton(R.string.workout_exit_positive) { _, _ ->


                            //findNavController()
                            //    .navigate(R.id.action_fragmentWorkout_to_fragmentHome)

                           //findNavController().navigate(R.id.action_fragmentWorkout_to_fragmentHome)
                            findNavController().popBackStack(R.id.fragmentWorkout, true)
                            //findNavController().navigate(R.id.fragmentHome)

                            // TODO make the dialog 'exit' functionality.
                            //  For some reason, the pop back stack doesnt work when overriding the back press

                            }

                        .setNegativeButton(R.string.workout_exit_negative, null)
                        .setOnDismissListener{resume()}
                        .show()
                }
            }
        //requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val parent:View = inflater.inflate(R.layout.fragment_workout, container, false)
        findViews(parent)
        initPreparation()
        initTimers()
        initProgress()
        initButtons()



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
        animationImage = parent.findViewById(R.id.workout_IMG_animation)
        equipmentText = parent.findViewById(R.id.workout_LBL_equipment)

    }

    private fun initTimers(){
        startTimerForNextExercise()

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                if(!paused) {
                    workoutLog.totalDuration = workoutLog.totalDuration.plus(1)
                    updateDurationTimer(workoutLog.totalDuration)
                    Log.i("pttt", "duratrion: "  +workoutLog.totalDuration)
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

    private fun initPreparation(){
        prepTimeSeconds = (MySettingsScreen.getInstance().getTileByTitle("Preparation Time") as SeekbarTileData).savedValue

        if(prepTimeSeconds > 0)
            exercises.add(0, Exercise(timeInSeconds = prepTimeSeconds, name = "Get Ready", isBreak = true))

    }


    private fun startTimerForNextExercise(){
        if(currentExercise == exercises.size){
            workoutDone()
            return
        }
        updateNextPrevButtonsVisibility()
        updateProgressBar()
        updateNextUpText()
        updateEquipmentText()
        updateAnimation()


        if(ttsEnabled)
            tts.speak(exercises[currentExercise].name,TextToSpeech.QUEUE_FLUSH,null, null)

        if(exercises[currentExercise].isBreak)
            timerView.setProgressColor(ContextCompat.getColor(requireContext(),R.color.color_break))
        else
            timerView.setProgressColor(ContextCompat.getColor(requireContext(),R.color.progress_bar))


        timerView.setCircularTimerListener(object : CircularTimerListener{
            override fun updateDataOnTick(remainingTimeInMs: Long): String {
                return generateTimeTextFromSeconds((remainingTimeInMs / 1000f).toInt())
            }

            override fun onTimerFinished() {
                // Currently we are tracking each exercise that is fully complete via timer
                // When user skips/goes back to other exercise it will not be saved
                if(!exercises[currentExercise].isBreak) {
                    workoutLog.trackExerciseDone(exercises[currentExercise], user.weight)
                }
                currentExercise++
                startTimerForNextExercise()
                // Next timer of next exercise will start
            }
        }, exercises[currentExercise].timeInSeconds!!.toLong(), TimeFormatEnum.SECONDS,30)

        timerView.startTimer()
        timerView.prefix = capitalizeWords(exercises[currentExercise].name!!)

    }

    private fun updateEquipmentText() {

        val equipmentList = exercises[currentExercise].equipment
        if(equipmentList == null || equipmentList.isEmpty()) {
            equipmentText.text = ""
            return
        }

        val builder : StringBuilder = StringBuilder()
        builder.append("With ")
        for(eq in exercises[currentExercise].equipment!!){
            builder.append(eq.displayName + ", ")
        }

        equipmentText.text = builder.removeSuffix(", ")


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


    private fun updateAnimation(){
        if(exercises[currentExercise].animationId==null) {
            Glide.with(this).clear(animationImage)
            return
        }

        val id = resources.getIdentifier(
            ANIMATION_RESOURCE_NAME_PREFIX+exercises[currentExercise].animationId,
            "raw", context?.packageName)

        Glide.with(this).asGif().load(id).into(animationImage)
    }

    private fun initButtons(){
        pauseResumeButton.setOnClickListener {
            togglePauseResume()
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

    private fun togglePauseResume(){
        if(paused){
            resume()
        }
        else
            pause()
    }

    private fun pause(){
        paused = true
        timerView.pause()
        pauseResumeButton.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
    }


    private fun resume(){
        paused = false
        timerView.resume()
        pauseResumeButton.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
    }

    override fun onPause() {
        super.onPause()
        pause()
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }


    //region TTS
    override fun onInit(status: Int) {
        ttsEnabled = (MySettingsScreen.getInstance().getTileByTitle("Text to Speech") as ToggleTileData).savedValue
        if(!ttsEnabled)
            return
    }




    //endregion

}