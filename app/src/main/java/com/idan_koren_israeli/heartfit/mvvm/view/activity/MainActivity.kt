package com.idan_koren_israeli.heartfit.mvvm.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.db.firebase.auth.AuthManager
import com.idan_koren_israeli.heartfit.mvvm.view.fragment.*


class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var mainActivity_bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AuthManager.setActivity(this)
        initNavigation()


        //show the bottom navigation only to the relevant fragments
        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.fragmentHistory, R.id.fragmentHome -> mainActivity_bottomNavigation.visibility = View.VISIBLE
                    else -> mainActivity_bottomNavigation.visibility = View.GONE
                }
            }

        //prevent the user to lunch the same fragment over and over
        mainActivity_bottomNavigation.setOnItemReselectedListener { /*no op*/ }
    }

    // This is for debugging usage
    private fun createEquipmentSelectFragment(): FragmentEquipmentSelect {
        val fragment = FragmentEquipmentSelect()
        fragment.onSelectionDone = { equipmentSelectedList ->
            val equipmentNames: MutableSet<String> = mutableSetOf()
            equipmentSelectedList.forEach { eqSelected -> equipmentNames.add(eqSelected.displayName!!) }
            CommonUtils.getInstance().showToast(equipmentNames.toString())

        }
        return fragment
    }

    private fun initNavigation() {
        //set navigation
        mainActivity_bottomNavigation = findViewById(R.id.mainActivity_bottomNavigation)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainActivity_fragment) as NavHostFragment
        mainActivity_bottomNavigation.setupWithNavController(navHostFragment.findNavController())


        //prevent the user to click on the same fragment twice
        mainActivity_bottomNavigation.setOnItemReselectedListener {  /*no op*/ }

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