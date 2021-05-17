package com.idan_koren_israeli.heartfit.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.component.BottomNavigationManager
import com.idan_koren_israeli.heartfit.firebase.auth.AuthManager
import com.idan_koren_israeli.heartfit.fragment.FragmentHome


class MainActivity : AppCompatActivity() {



    companion object{
        const val RC_SIGN_IN:Int = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        BottomNavigationManager.initView(this@MainActivity,R.id.mainActivity_bottomNavigation);
        //AuthManager.initHandler(this)
        //DatabaseManager.testRealtimeDatabase()


        val homeFragment = FragmentHome()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActivity_fragment, homeFragment).commit()


    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        AuthManager.authScreenResult(requestCode,resultCode,data)

    }
}