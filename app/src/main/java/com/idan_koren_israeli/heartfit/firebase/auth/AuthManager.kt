package com.idan_koren_israeli.heartfit.firebase.auth

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.activity.MainActivity
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.model.User
import java.util.*

//This way we can create a singleton in kotlin
// src https://medium.com/swlh/singleton-class-in-kotlin-c3398e7fd76b

object AuthManager {

    private lateinit var auth : FirebaseAuth

    fun initAuth(){
        auth = FirebaseAuth.getInstance()
    }

    fun authScreenShow(activity:FragmentActivity){
        if (auth.currentUser != null) {
            // already signed in
        } else {
            activity.startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(
                        listOf(
                            AuthUI.IdpConfig.GoogleBuilder().build()
                        )
                    )
                    .build(),
                MainActivity.RC_SIGN_IN
            )
        }
    }

    fun authScreenResult(requestCode: Int, resultCode: Int, data: Intent?){
        if(requestCode == MainActivity.RC_SIGN_IN){
            val response: IdpResponse? = IdpResponse.fromResultIntent(data)

            if(resultCode == AppCompatActivity.RESULT_OK){
                CommonUtils.getInstance().showToast(auth.currentUser!!.displayName + " Authenticated")

                DatabaseManager.storeUser(User(uid = auth.currentUser!!.uid,hearts = 0))

            }
            else{
                if(response==null) {
                    CommonUtils.getInstance().showToast("Authenticated Cancelled")
                }
            }
        }
    }

    fun getCurrentUserName():String?{
        if(auth.currentUser==null)
            return null
        return auth.currentUser!!.displayName

    }

    fun handleSignIn(activity: MainActivity) {
        if(auth.currentUser==null)
            authScreenShow(activity);
        else
            Log.i("pttt","User Auth Auto-Detected " + auth.currentUser!!.uid)
    }

    fun signOut(activity: MainActivity){
        AuthUI.getInstance().signOut(activity).addOnCompleteListener {authScreenShow(activity)}

    }

}