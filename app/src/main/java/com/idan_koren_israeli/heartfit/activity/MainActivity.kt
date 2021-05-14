package com.idan_koren_israeli.heartfit.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.fragment.EquipmentSelectFragment


class MainActivity : AppCompatActivity() {

    private lateinit var db : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Firebase.database.reference
        db.child("test_root").setValue("test_value")


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val value: String? = dataSnapshot.getValue(String::class.java)
                Log.i("pttt", value!!)
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("pttt", "loadPost:onCancelled", databaseError.toException())
            }
        }
        db.child("test_root").addValueEventListener(postListener)

        val equipmentSelect = EquipmentSelectFragment()
        supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragment, equipmentSelect).commit()

    }
}