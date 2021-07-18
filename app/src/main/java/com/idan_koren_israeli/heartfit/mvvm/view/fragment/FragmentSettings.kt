package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.idan_koren_israeli.heartfit.R
import com.idankorenisraeli.mysettingsscreen.MySettingsScreen

class FragmentSettings : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        MySettingsScreen.getInstance().initSettingsScreen(activity, "My Settings Screen" )
        findNavController().navigate(R.id.action_fragmentSettings_to_fragmentHome)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}