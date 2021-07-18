package com.idan_koren_israeli.heartfit.mvvm.view.activity

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
import com.idan_koren_israeli.heartfit.mvvm.view.fragment.*
import com.idan_koren_israeli.heartfit.receiver.HeartFitReceiver
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var mainActivity_bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
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


    private fun initNavigation() {
        //set navigation
        mainActivity_bottomNavigation = findViewById(R.id.mainActivity_bottomNavigation)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainActivity_fragment) as NavHostFragment
        mainActivity_bottomNavigation.setupWithNavController(navHostFragment.findNavController())


        //prevent the user to click on the same fragment twice
        mainActivity_bottomNavigation.setOnItemReselectedListener {  /*no op*/ }

    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        // No call for super()
    }

    override fun onRestart() {
        super.onRestart()

        refreshHomeFragment()
    }

    private fun refreshHomeFragment(){
        val label : CharSequence = navHostFragment.findNavController().currentDestination!!.label!!
        if(label == "fragment_home") {
            finish()
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "HeartFitAppChannel"
            val description = "Channel for notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("HeartFit", channelName, importance)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        setNotification()
    }

    private fun setNotification(){
        //86400000 is one day in ms
        //add it to the timeInMillis before we submit
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, HeartFitReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis + 86400000,
            AlarmManager.INTERVAL_DAY,
            pendingIntent)
    }

}