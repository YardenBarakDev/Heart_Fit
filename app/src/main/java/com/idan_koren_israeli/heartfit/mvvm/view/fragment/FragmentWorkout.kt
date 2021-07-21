package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamzaahmedkhan.circulartimerview.CircularTimerListener
import com.hamzaahmedkhan.circulartimerview.TimeFormatEnum
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.databinding.FragmentWorkoutBinding
import com.idan_koren_israeli.heartfit.mvvm.model.*
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


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWorkout.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentWorkout : Fragment(R.layout.fragment_workout), TextToSpeech.OnInitListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private val WORKOUT_KEY = "workout"
    private val EXERCISES_KEY = "exercises"
    private val USER_KEY = "user"
    private val ANIMATION_RESOURCE_NAME_PREFIX = "anim_exercise_"


    private lateinit var binding : FragmentWorkoutBinding
    private lateinit var workout: Workout
    private lateinit var user: User
    private lateinit var exercises: ArrayList<Exercise>

    private var prepTimeSeconds: Int = 0
    private lateinit var mainHandler : Handler

    private var totalWorkoutTime: Int = 0
    private var currentExercise: Int = 0
    private lateinit var tts: TextToSpeech
    private var ttsEnabled: Boolean = false
    private var paused: Boolean = false
    private lateinit var workoutLog: WorkoutLog

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Redirect to our own function
            this@FragmentWorkout.onBackPressed()
        }
    }

    private fun onBackPressed() {
        // Work your magic! Show dialog etc.
        var navigatorStackPop = false

        pause()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.workout_exit_title)
            .setMessage(R.string.workout_exit_message)
            .setPositiveButton(R.string.workout_exit_positive) { _, _ ->

                //onPause()
                //onStop()
                //onDestroy()

                findNavController().popBackStack()
                navigatorStackPop = true

            }

            .setNegativeButton(R.string.workout_exit_negative, null)
            .setOnDismissListener {
                if(!navigatorStackPop) resume() }
            .show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workout = it.getSerializable(WORKOUT_KEY) as Workout
            exercises = it.getSerializable(EXERCISES_KEY) as ArrayList<Exercise>
            user = it.getSerializable(USER_KEY) as User

            workoutLog = WorkoutLog(workout, 0, arrayListOf(), 0, totalExercisesCount = exercises.size)
        }


    }

    override fun onResume() {
        super.onResume()
        tts = TextToSpeech(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWorkoutBinding.bind(view)
        initPreparation()
        initTimers()
        initProgress()
        initButtons()
        (requireActivity() as AppCompatActivity).apply {
            // Redirect system "Back" press to our dispatcher
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedDispatcher)

        }
    }

    override fun onDestroyView() {
        backPressedDispatcher.remove()
        super.onDestroyView()
    }

    private fun initTimers() {
        startTimerForNextExercise()

        mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                if (!paused) {
                    workoutLog.totalDuration = workoutLog.totalDuration.plus(1)
                    updateDurationTimer(workoutLog.totalDuration)
                }
                mainHandler.postDelayed(this, 1000)
            }
        })


        binding.workoutCTVTimer.typefaceBold = resources.getFont(R.font.saira_condensed_bold)
        binding.workoutCTVTimer.typefaceRegular = resources.getFont(R.font.saira_condensed_regular)
    }

    private fun stopHandler(){
        mainHandler.removeMessages(0)
    }


    private fun initProgress() {
        totalWorkoutTime = exercises.fold(0, { acc, exercise -> acc + exercise.timeInSeconds!! })
        binding.workoutPRGProgressBar.value = 0f

        binding.workoutPRGProgressBar.textTitle = capitalizeWords(workout.name!!)
        binding.workoutLBLHeartCount.text = workout.heartsValue.toString()
    }

    private fun initPreparation() {
        prepTimeSeconds = (MySettingsScreen.getInstance().getTileByTitle("Preparation Time") as SeekbarTileData).savedValue

        if (prepTimeSeconds > 0)
            exercises.add(0, Exercise(timeInSeconds = prepTimeSeconds, name = "Get Ready", isBreak = true))

    }


    private fun startTimerForNextExercise() {

        if (currentExercise == exercises.size) {
            workoutDone()
            return
        }
        updateNextPrevButtonsVisibility()
        updateProgressBar()
        updateNextUpText()
        updateEquipmentText()
        updateAnimation()


        if (ttsEnabled){
            tts.speak(exercises[currentExercise].name, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        if (exercises[currentExercise].isBreak)
            binding.workoutCTVTimer.setProgressColor(ContextCompat.getColor(requireContext(), R.color.color_break))
        else
            binding.workoutCTVTimer.setProgressColor(ContextCompat.getColor(requireContext(), R.color.progress_bar))


        binding.workoutCTVTimer.setCircularTimerListener(object : CircularTimerListener {
            override fun updateDataOnTick(remainingTimeInMs: Long): String {
                return generateTimeTextFromSeconds((remainingTimeInMs / 1000f).toInt())
            }

            override fun onTimerFinished() {
                // Currently we are tracking each exercise that is fully complete via timer
                // When user skips/goes back to other exercise it will not be saved
                if (!exercises[currentExercise].isBreak) {
                    workoutLog.trackExerciseDone(exercises[currentExercise], user.weight)
                }
                currentExercise++
                if(!paused)
                    startTimerForNextExercise()
                // Next timer of next exercise will start
            }
        }, exercises[currentExercise].timeInSeconds!!.toLong(), TimeFormatEnum.SECONDS, 30)

        binding.workoutCTVTimer.startTimer()
        binding.workoutCTVTimer.prefix = capitalizeWords(exercises[currentExercise].name!!)

    }

    private fun updateEquipmentText() {

        val equipmentList = exercises[currentExercise].equipment
        if (equipmentList == null || equipmentList.isEmpty()) {
            binding.workoutLBLEquipment.text = ""
            return
        }

        val builder: StringBuilder = StringBuilder()
        builder.append("With ")
        for (eq in exercises[currentExercise].equipment!!) {
            builder.append(eq.displayName + ", ")
        }

        binding.workoutLBLEquipment.text = builder.removeSuffix(", ")


    }

    private fun generateTimeTextFromSeconds(numOfSeconds: Int): String {
        val minutes = numOfSeconds / 60
        val seconds = ceil(((numOfSeconds % 60)).toDouble()).toInt()
        val stb: StringBuilder = java.lang.StringBuilder()
        if (minutes < 10)
            stb.append('0')
        stb.append(minutes)
        stb.append(':')
        if (seconds < 10)
            stb.append('0')
        stb.append(seconds)
        return stb.toString()
    }

    private fun updateProgressBar() {
        binding.workoutPRGProgressBar.value = calculateProgressBarValue()
    }


    private fun updateAnimation() {
        if (exercises[currentExercise].animationId == null) {
            Glide.with(this).clear(binding.workoutIMGAnimation)
            return
        }

        val id = resources.getIdentifier(
            ANIMATION_RESOURCE_NAME_PREFIX + exercises[currentExercise].animationId,
            "raw", context?.packageName
        )

        Glide.with(this).asGif().load(id).into(binding.workoutIMGAnimation)
    }

    private fun initButtons() {
        binding.workoutBTNPauseResume.setOnClickListener {
            togglePauseResume()
        }
        binding.workoutBTNNext.setOnClickListener {
            if(!exercises[currentExercise].isBreak){
                workoutLog.exercisesSkipped += 1
            }
            currentExercise++
            startTimerForNextExercise()
        }
        binding.workoutBTNPrev.setOnClickListener {
            currentExercise--
            startTimerForNextExercise()
        }
    }

    private fun updateNextPrevButtonsVisibility() {
        if (currentExercise == 0)
            binding.workoutBTNPrev.visibility = View.INVISIBLE
        else
            binding.workoutBTNPrev.visibility = View.VISIBLE

        if (currentExercise == exercises.size - 1)
            binding.workoutBTNNext.visibility = View.INVISIBLE
        else
            binding.workoutBTNNext.visibility = View.VISIBLE

    }

    private fun updateNextUpText() {
        if (currentExercise == exercises.size - 1) {
            binding.workoutLBLNextExercise.visibility = View.INVISIBLE
            binding.workoutLBLNextTitle.visibility = View.INVISIBLE
        } else {
            binding.workoutLBLNextTitle.visibility = View.VISIBLE
            binding.workoutLBLNextExercise.visibility = View.VISIBLE
            binding.workoutLBLNextExercise.text = capitalizeWords(exercises[currentExercise + 1].name!!)
        }


    }

    private fun capitalizeWords(orgText: String): String {
        return orgText.split(' ').joinToString(" ") { it ->
            it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        }
    }


    private fun calculateProgressBarValue(): Float {
        var timePastExercises = 0
        exercises.forEachIndexed lit@{ index, exercise ->
            if (index > currentExercise) return@lit
            timePastExercises += exercise.timeInSeconds!!
        } // local return to the caller of the lambda

        return timePastExercises / totalWorkoutTime.toFloat()
    }

    private fun workoutDone() {

        val bundle = bundleOf("workout_log" to workoutLog)

        //move to workoutFinished fragment
        findNavController().navigate(R.id.action_fragmentWorkout_to_fragmentWorkoutFinished, bundle)

    }

    companion object {
        @JvmStatic
        fun newInstance(workout: Workout, exercises: List<Exercise>, user: User) =
            FragmentWorkout().apply {
                arguments = Bundle().apply {
                    putSerializable(WORKOUT_KEY, workout)
                    putSerializable(EXERCISES_KEY, exercises as Serializable)
                    putSerializable(USER_KEY, user)
                }
            }
    }

    private fun updateDurationTimer(seconds: Int) {
        binding.workoutLBLDuration.text =
            String.format(
                "%02d:%02d:%02d", seconds / 3600,
                (seconds % 3600) / 60, (seconds % 60)
            )
    }

    private fun togglePauseResume() {
        if (paused) {
            resume()
        } else
            pause()
    }

    private fun pause() {
        paused = true
        binding.workoutCTVTimer.pause()
        binding.workoutBTNPauseResume.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
    }


    private fun resume() {
        paused = false
        binding.workoutCTVTimer.resume()
        binding.workoutBTNPauseResume.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
    }

    override fun onPause() {
        super.onPause()
        pause()
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        stopHandler()
        super.onDestroy()
    }


    //region TTS
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS)
            ttsEnabled = (MySettingsScreen.getInstance().getTileByTitle("Text to Speech") as ToggleTileData).savedValue
        if(status == TextToSpeech.ERROR){
            Log.i(FragmentWorkout::class.java.simpleName, "Error initiating text to speech, please check device settings")
        }
        if (!ttsEnabled)
            return
    }
    //endregion

}