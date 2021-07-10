package com.idan_koren_israeli.heartfit.db.firebase.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.idan_koren_israeli.heartfit.mvvm.model.User


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

    // This function should be called
    fun loadCurrentUser(onLoaded: (userLoaded: User?) -> Unit){
        db.child(KEY_USERS)
            .child(FirebaseAuth.getInstance().uid!!).get().addOnSuccessListener {
                onLoaded.invoke(it.getValue<User>())
            }
    }


}