package com.idan_koren_israeli.heartfit.firebase.auth

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.activity.MainActivity
import java.util.*

//This way we can create a singleton in kotlin
// src https://medium.com/swlh/singleton-class-in-kotlin-c3398e7fd76b

object AuthManager {

    fun initHandler(activity:FragmentActivity){
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
        } else {
            activity.startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(
                        Arrays.asList(
                            AuthUI.IdpConfig.GoogleBuilder().build()
                        )
                    )
                    .build(),
                MainActivity.RC_SIGN_IN
            )
        }
    }

    fun resultHandler(requestCode: Int, resultCode: Int, data: Intent?){
        if(requestCode == MainActivity.RC_SIGN_IN){
            val response: IdpResponse? = IdpResponse.fromResultIntent(data)

            if(resultCode == AppCompatActivity.RESULT_OK){
                //
                Log.i("pttt", "Auth Successful")
            }
            else{
                if(response==null) {
                    Log.i("pttt", "Auth Cancelled")
                }
            }
        }
    }

}