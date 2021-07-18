package com.idan_koren_israeli.heartfit.common

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.component.KotlinPrefsManager
import com.idan_koren_israeli.heartfit.dialog.DialogWeightPicker
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.firebase.firestore.FirestoreManager
import com.idan_koren_israeli.heartfit.db.room_db.HeartFitRoomDB
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
import com.idankorenisraeli.mysettingsscreen.MySettingsScreen
import com.idankorenisraeli.mysettingsscreen.common.SharedPrefsManager
import com.idankorenisraeli.mysettingsscreen.enums.ToggleType
import com.idankorenisraeli.mysettingsscreen.tile_data.dialog.MultiChoiceTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.ButtonTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.DividerTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.SettingsTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.essential.TitleTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.view.SeekbarTileData
import com.idankorenisraeli.mysettingsscreen.tile_data.view.ToggleTileData
import com.pixplicity.easyprefs.library.Prefs


class MyApp : MultiDexApplication() {

    val mFTActivityLifecycleCallbacks = MyAppLifecycleCallbacks()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()

        DatabaseManager.initDatabase()
        FirestoreManager.initFirestore()
        HeartFitRoomDB.init(this)
        CommonUtils.initHelper(this)
        initSettingsScreen()
        initSharedPrefs()

        //CurrentUserDataModelView.loadCurrentUser { /*Fetching the user from server */ }

        registerActivityLifecycleCallbacks(mFTActivityLifecycleCallbacks)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    private fun initSharedPrefs(){
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        KotlinPrefsManager.init(this)
    }


    private fun initSettingsScreen(){



        val equipmentNames :ArrayList<String> = arrayListOf()
        for(eq in Equipment.values()){
            equipmentNames.add(eq.displayName)
        }

        val selectEquipmentTile: MultiChoiceTileData = MultiChoiceTileData("Select Equipment", "What equipment do you have at home?")
            .withIconId(R.drawable.ic_dumbbell)
            .withOptionsList(equipmentNames)
            .withOnChangedListener{ options, checked ->
                val myEquipment = java.util.ArrayList<Equipment?>()
                for(i in 0 until options.size){
                    if(checked[i]) {
                        myEquipment.add(Equipment.fromInt(i)!!)
                    }
                }
                KotlinPrefsManager.saveArrayList(myEquipment,CommonUtils.KEY_EQUIPMENT)
                Log.i("pttt", "SAVING myEquipment "  +checked)

            }

        val prepTimeTile :SeekbarTileData = SeekbarTileData("Preparation Time", "Pre-Workout delay in seconds")
            .withMaxValue(30)
            .withMinValue(0)
            .withDefaultValue(0)
            .withIconId(R.drawable.ic_baseline_timer_24)


        val ttsTile : ToggleTileData = ToggleTileData("Text to Speech", "Exercise name voice assistance")
            .withToggleType(ToggleType.SWITCH).withDefaultValue(true)
            .withIconId(R.drawable.ic_baseline_record_voice_over_24)

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

                // TODO Add Loading Screen (Calling the server here)
                val settingsActivity:Activity? = currentActivity()


                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(settingsActivity as Context)
                val weightPicker = DialogWeightPicker()
                weightPicker.inflateLayout(LayoutInflater.from(this))
                weightPicker.showDialog(materialAlertDialogBuilder, DatabaseManager.currentUser.weight!!)


            }
            .withIconId(R.drawable.ic_baseline_accessibility_new_24)

        val dataTiles: ArrayList<SettingsTileData> = ArrayList()

        dataTiles.add(selectEquipmentTile)
        dataTiles.add(prepTimeTile)
        dataTiles.add(weightTile)
        dataTiles.add(ttsTile)
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