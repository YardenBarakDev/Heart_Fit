package com.idan_koren_israeli.heartfit.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
import java.lang.reflect.Type
import java.util.ArrayList

@SuppressLint("StaticFieldLeak")
object KotlinPrefsManager {

    lateinit var context:Context

    fun init(context:Context){
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
}