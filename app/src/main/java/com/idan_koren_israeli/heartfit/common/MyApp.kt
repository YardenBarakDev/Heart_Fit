package com.idan_koren_israeli.heartfit.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.dialog.DialogWeightPicker
import com.idan_koren_israeli.heartfit.firebase.auth.AuthManager
import com.idan_koren_israeli.heartfit.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.firebase.firestore.FirestoreManager
import com.idankorenisraeli.mysettingsscreen.MySettingsScreen
import com.idankorenisraeli.mysettingsscreen.activity.MySettingsActivity
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.ButtonTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.DividerTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.SettingsTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.TitleTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.view.SeekbarTileData
import com.pixplicity.easyprefs.library.Prefs



class MyApp : MultiDexApplication() {

    val mFTActivityLifecycleCallbacks = MyAppLifecycleCallbacks()

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()

        AuthManager.initAuth()
        DatabaseManager.initDatabase()
        FirestoreManager.initFirestore()
        CommonUtils.initHelper(this)
        initSettingsScreen()
        initSharedPrefs()

        registerActivityLifecycleCallbacks(mFTActivityLifecycleCallbacks)

       // DatabaseManager.loadCurrentUser {  /*no op, just to fetch it from server while loading the app  */ }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    private fun initSharedPrefs(){
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    private fun initSettingsScreen(){


        val weightPicker:DialogWeightPicker = DialogWeightPicker()
        weightPicker.inflateLayout(LayoutInflater.from(this))

        val selectEquipmentTile: ButtonTileData = ButtonTileData("Select Equipment", "What do you have at home?")
            .withIconId(R.drawable.ic_dumbbell)
            .withOnClickListener {
                //Show Fragment of Equipmnet Selection and use a callback to update the result
                Log.i("pttt", "Showing equipment select menu.")

            }

        val prepTimeTile :SeekbarTileData = SeekbarTileData("Preparation Time", "Pre-Workout delay in seconds")
            .withMaxValue(30)
            .withMinValue(0)
            .withDefaultValue(0)
            .withIconId(R.drawable.ic_baseline_timer_24)

        //TODO Voice announcment checkbox tile if will be implemented

        val logOutTile:ButtonTileData = ButtonTileData("Log Out", "Currently logged in as " + AuthManager.getAuthUserName())
            .withIconId(ButtonTileData.IC_INVISIBLE)
            .withOnClickListener {
                // Log out from account and back to main screen
                // TODO Handle navigation graph
                AuthManager.signOut()
                Log.i("pttt", "Logging Out")
            }

        val horizontalLineTile : DividerTileData = DividerTileData(2)

        val infoTile : TitleTileData = TitleTileData("HeatFit Workout", "© 2021 Version 0.0.1")

        val termsOfUseTime: ButtonTileData = ButtonTileData("Terms of Use","Read before using this app").withOnClickListener {
            //Opens Terms of use fragment
            // TODO Handle navigation graph
            // TODO Create Terms of Use Fragment
            Log.i("pttt", "Showing terms of use..")
        }

        val weightTile :ButtonTileData = ButtonTileData("Your Weight", "For calories burned calculation")
            .withOnClickListener{
                // We know this callback will be called in the settings activity:
                val settingsActivity:Activity? = currentActivity()
                materialAlertDialogBuilder = MaterialAlertDialogBuilder(settingsActivity as Context)
                weightPicker.showDialog(materialAlertDialogBuilder)


                //TODO Create Weight selection fragment (with save to sp)
            }
            .withIconId(R.drawable.ic_baseline_accessibility_new_24)

        val dataTiles: ArrayList<SettingsTileData> = ArrayList()

        dataTiles.add(selectEquipmentTile)
        dataTiles.add(prepTimeTile)
        dataTiles.add(weightTile)
        dataTiles.add(logOutTile)
        dataTiles.add(horizontalLineTile)
        dataTiles.add(termsOfUseTime)
        dataTiles.add(infoTile)

        MySettingsScreen.getInstance().tilesData = dataTiles
    }

    companion object {
        private var instance: MyApp? = null

        fun currentActivity(): Activity? {
            return instance!!.mFTActivityLifecycleCallbacks.currentActivity
        }
    }
}