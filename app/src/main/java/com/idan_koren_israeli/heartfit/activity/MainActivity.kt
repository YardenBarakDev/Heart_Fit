package com.idan_koren_israeli.heartfit.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.firebase.auth.AuthManager
import com.idan_koren_israeli.heartfit.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.fragment.EquipmentSelectFragment


class MainActivity : AppCompatActivity() {



    companion object{
        const val RC_SIGN_IN:Int = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        //AuthManager.initHandler(this)

        DatabaseManager.testRealtimeDatabase()


        val equipmentSelect = EquipmentSelectFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActivity_fragment, equipmentSelect).commit()

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        AuthManager.resultHandler(requestCode,resultCode,data)

    }
}