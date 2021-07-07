package com.idan_koren_israeli.heartfit.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.firebase.auth.AuthManager
import com.idan_koren_israeli.heartfit.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.firebase.firestore.FirestoreManager
import com.idan_koren_israeli.heartfit.fragment.*
import com.idan_koren_israeli.heartfit.model.*


class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var mainActivity_bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AuthManager.setActivity(this)

        initNavigation()

        val splashFragment : FragmentSplash = FragmentSplash.newInstance(R.mipmap.ic_launcher)
        splashFragment.setOnAnimationFinishListener {
            supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragment, FragmentHome()).commitAllowingStateLoss()

            // This initializes a worokout from loaded data from firestore::

//            FirestoreManager.loadExercisesByLevel(ExerciseLevel.Basic) { loaded ->
//                Log.i("pttt", "Loaded Exercises: ${loaded.size}")
//                val workout = Workout("My Abs Workout", listOf(Equipment.Bench), listOf(MuscleGroup.ARMS), 6, 0, WorkoutLevel.Basic)
//                supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragment, FragmentWorkout.newInstance(workout ,loaded)).commitAllowingStateLoss()
//            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragment,
            splashFragment
        ).commitAllowingStateLoss()

        AuthManager.handleSignIn()




        //control when the bottomNavigation will be visible according to the fragment presented
       // navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
       //     when (destination.id) {
       //         R.id.fragmentHistory, R.id.fragmentHome -> mainActivity_bottomNavigation.visibility = View.VISIBLE
       //         else -> mainActivity_bottomNavigation.visibility = View.GONE
       //     }
       // }
    }

    // This is for debugging usage
    private fun createEquipmentSelectFragment() : FragmentEquipmentSelect{
        val fragment = FragmentEquipmentSelect()
        fragment.onSelectionDone = { equipmentSelectedList ->
            val equipmentNames : MutableSet<String> = mutableSetOf()
            equipmentSelectedList.forEach { eqSelected -> equipmentNames.add(eqSelected.displayName!!) }
            CommonUtils.getInstance().showToast(equipmentNames.toString())

        }
        return fragment
    }

    private fun initNavigation(){
        //set navigation
        mainActivity_bottomNavigation = findViewById(R.id.mainActivity_bottomNavigation)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivity_fragment) as NavHostFragment
        mainActivity_bottomNavigation.setupWithNavController(navHostFragment.findNavController())





        //prevent the user to click on the same fragment twice
        mainActivity_bottomNavigation.setOnNavigationItemReselectedListener {  /*no op*/ }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("pttt", "Activity Result")
        AuthManager.authScreenResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        // No call for super()
    }
}