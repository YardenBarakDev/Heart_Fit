package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.databinding.FragmentTopBarBinding
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.mvvm.view_model.TopBarViewModel

class FragmentTopBar : Fragment(R.layout.fragment_top_bar) {

    private lateinit var binding : FragmentTopBarBinding
    private val topBarViewModel = TopBarViewModel(DatabaseManager.currentUser.uid!!)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopBarBinding.bind(view)
        setClickListeners()
        setData()

    }
    private fun setData() {
        binding.topBarLBLHeartCount.text = DatabaseManager.currentUser.hearts.toString()


        topBarViewModel.totalCaloriesBurnedFromLastSevenDays.observe(viewLifecycleOwner, {
            if (it != null){
                binding.topBarLBLCalories.text = it.toString()
            }

        })
        topBarViewModel.totalTimeFromLastSevenDays.observe(viewLifecycleOwner, {
            if (it != null){
                binding.topBarLBLHours.text = topBarViewModel.calculateHoursFromSeconds(it)
            }
        })
        topBarViewModel.totalWorkoutsFromPastSevenDays.observe(viewLifecycleOwner,{
            if (it != null) {

                binding.topBarLBLWorkouts.text = it.toString()
            }
        })

    }

    private fun setClickListeners(){
        binding.topBarBTNSignOut.setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
            mGoogleSignInClient.signOut()
            FirebaseAuth.getInstance().signOut()

            findNavController().navigate(R.id.action_fragmentHome_to_fragmentAuth)
        }
    }
}