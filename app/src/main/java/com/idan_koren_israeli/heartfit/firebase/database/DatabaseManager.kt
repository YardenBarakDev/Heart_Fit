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
import com.idan_koren_israeli.heartfit.model.User
import java.util.*



//RealtimeDatabase
object DatabaseManager {
    private lateinit var db: DatabaseReference

    fun initDatabase(){
        db = Firebase.database.reference
    }

    private val KEY_USERS = "users"

    fun storeUser(user: User){
        db.child(KEY_USERS).child(user.uid!!).setValue(user)
    }

}