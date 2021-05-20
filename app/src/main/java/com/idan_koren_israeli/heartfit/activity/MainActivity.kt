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


class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var mainActivity_bottomNavigation: BottomNavigationView


    companion object{
        const val RC_SIGN_IN:Int = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set navigation
        mainActivity_bottomNavigation = findViewById(R.id.mainActivity_bottomNavigation)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivity_fragment) as NavHostFragment
        mainActivity_bottomNavigation.setupWithNavController(navHostFragment.findNavController())

        //prevent the user to click on the same fragment twice
        mainActivity_bottomNavigation.setOnNavigationItemReselectedListener {  /*no op*/ }

        //control when the bottomNavigation will be visible according to the fragment presented
       // navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
       //     when (destination.id) {
       //         R.id.fragmentHistory, R.id.fragmentHome -> mainActivity_bottomNavigation.visibility = View.VISIBLE
       //         else -> mainActivity_bottomNavigation.visibility = View.GONE
       //     }
       // }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        AuthManager.authScreenResult(requestCode, resultCode, data)

    }
}