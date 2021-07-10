package com.idan_koren_israeli.heartfit.db.firebase.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.idan_koren_israeli.heartfit.mvvm.model.User


//RealtimeDatabase
object DatabaseManager {
    private lateinit var db: DatabaseReference

    lateinit var currentUser: User

    fun initDatabase() {
        db = Firebase.database.reference
    }

    private val KEY_USERS = "users"

    fun storeUser(user: User, onStored: () -> Unit = {}) {
        db.child(KEY_USERS).child(user.uid!!).setValue(user)
            .addOnSuccessListener { onStored.invoke() }
    }

    fun storeCurrentUser(onStored: () -> Unit = {}) {
        db.child(KEY_USERS).child(currentUser.uid!!).setValue(currentUser)
            .addOnSuccessListener { onStored.invoke() }
    }

    fun loadUser(uid: String, onLoaded: (userLoaded: User?) -> Unit) {
        db.child(KEY_USERS)
            .child(uid).get().addOnSuccessListener {
                onLoaded.invoke(it.getValue<User>())
            }
    }

    fun loadCurrentUser(fbUser: FirebaseUser, onLoaded: (currentUser: User) -> Unit) {
        loadUser(fbUser.uid) {
            if (it == null) {
                // New User
                currentUser = User(fbUser.uid, fbUser.displayName)
                storeUser(currentUser) { onLoaded.invoke(currentUser) }
            }
            else{
                currentUser = it
                onLoaded.invoke(currentUser)
            }
        }
    }


}