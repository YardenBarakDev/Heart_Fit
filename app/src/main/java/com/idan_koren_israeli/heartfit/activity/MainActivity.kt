package com.idan_koren_israeli.heartfit.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.component.BottomNavigationManager
import com.idan_koren_israeli.heartfit.firebase.auth.AuthManager
import com.idan_koren_israeli.heartfit.fragment.FragmentWorkout
import com.idan_koren_israeli.heartfit.model.Workout
import com.idan_koren_israeli.heartfit.model.exercise.Exercise
import com.idan_koren_israeli.heartfit.model.exercise.RepsAction


class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var mainActivity_bottomNavigation: BottomNavigationView


    companion object{
        const val RC_SIGN_IN:Int = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initNavigation()



        supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragment,
            FragmentWorkout.newInstance(Workout(1,"Workout Name", listOf(RepsAction(Exercise(123),10))))
        ).commit()

        //control when the bottomNavigation will be visible according to the fragment presented
       // navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
       //     when (destination.id) {
       //         R.id.fragmentHistory, R.id.fragmentHome -> mainActivity_bottomNavigation.visibility = View.VISIBLE
       //         else -> mainActivity_bottomNavigation.visibility = View.GONE
       //     }
       // }
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
        AuthManager.authScreenResult(requestCode, resultCode, data)

    }
}