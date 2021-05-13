package com.idan_koren_israeli.heartfit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.fragment.EquipmentSelectFragment
import com.idan_koren_israeli.heartfit.model.exercise.Exercise

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val equipmentSelect = EquipmentSelectFragment()

        supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragment, equipmentSelect).commit()

    }
}