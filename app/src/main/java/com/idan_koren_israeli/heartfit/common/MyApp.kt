package com.idan_koren_israeli.heartfit.common

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.firebase.auth.AuthManager
import com.idankorenisraeli.mysettingsscreen.MySettingsScreen
import com.idankorenisraeli.mysettingsscreen.enums.ToggleType
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.*
import com.idankorenisraeli.mysettingsscreen.tile_data.view.SeekbarTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.view.ToggleTileData


class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()

        AuthManager.initAuth()
        initSettingsScreen()

    }

    private fun initSettingsScreen(){

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

        //Voice announcment checkbox tile if will be implemented

        val logOutTile:ButtonTileData = ButtonTileData("Log Out", "Currently logged in as " + AuthManager.getCurrentUserName())
            .withIconId(ButtonTileData.IC_INVISIBLE)
            .withOnClickListener {
                // Log out from account and back to main screen
                // TODO Implement Logout system
                Log.i("pttt", "Logging Out")
            }

        val horizontalLineTile : DividerTileData = DividerTileData(2)

        val infoTile : TitleTileData = TitleTileData("HeatFit Workout", "© 2021 Version 0.0.1")

        val termsOfUseTime: ButtonTileData = ButtonTileData("Terms of Use","Read before using this app").withOnClickListener {
            //Opens Terms of use fragment
            // TODO create terms of use
            Log.i("pttt", "Showing terms of use..")
        }

        val dataTiles: ArrayList<SettingsTileData> = ArrayList()

        dataTiles.add(selectEquipmentTile)
        dataTiles.add(prepTimeTile)
        dataTiles.add(logOutTile)
        dataTiles.add(horizontalLineTile)
        dataTiles.add(termsOfUseTime)
        dataTiles.add(infoTile)

        MySettingsScreen.getInstance().tilesData = dataTiles;
    }
}