package com.idan_koren_israeli.heartfit.firebase.database

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.idan_koren_israeli.heartfit.activity.MainActivity
import java.util.*

//RealtimeDatabase
object DatabaseManager {
    private lateinit var db: DatabaseReference

    fun testRealtimeDatabase(){
        db = Firebase.database.reference
        db.child("test_root").setValue("test_value")


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val value: String? = dataSnapshot.getValue(String::class.java)
                Log.i("pttt", value!!)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("pttt", "loadPost:onCancelled", databaseError.toException())
            }
        }
        db.child("test_root").addValueEventListener(postListener)

    }

}