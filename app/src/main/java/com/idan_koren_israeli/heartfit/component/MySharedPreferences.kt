package com.idan_koren_israeli.heartfit.db.sharedpreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
import java.lang.reflect.Type

@SuppressLint("StaticFieldLeak")
object MySharedPreferences {

    val name = "HEARTFIT_SF"
    val daysStrike = "DAY_STRIKE"
    val timestamp = "TIME_STAMP"

    lateinit var sharedPref : SharedPreferences
    lateinit var context:Context


    fun init(context : Context){
        sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        this.context = context
    }


    fun saveArrayList(list: java.util.ArrayList<Equipment?>?, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String?): java.util.ArrayList<Equipment?>? {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json: String = prefs.getString(key, "")!!
        val type: Type = object : TypeToken<List<Equipment?>>() {}.getType()
        return gson.fromJson(json, type)
    }


    fun getLatestTimeStamp() : Long{
        return sharedPref.getLong(timestamp, 0)
    }

    fun setNewTimeStamp(timeStamp : Long){
        sharedPref.edit {
            putLong(timestamp, timeStamp)
        }
    }

    fun getLatestDayStrike() : Int{
        return sharedPref.getInt(daysStrike, 0)
    }

    fun setNewDayStrike(dayStrike : Int){
        sharedPref.edit {
            putInt(daysStrike, dayStrike)
        }
    }

}