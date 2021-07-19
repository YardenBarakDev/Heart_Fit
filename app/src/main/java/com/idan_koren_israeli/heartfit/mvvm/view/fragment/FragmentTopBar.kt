package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.mvvm.view_model.TopBarViewModel

private const val HEART_COUNT = "param1"

class FragmentTopBar : Fragment() {
    private var heartCount: Int? = null

    private var heartCountText: TextView? = null
    private var top_bar_LBL_calories : TextView? = null
    private var top_bar_LBL_hours : TextView? = null
    private var top_bar_LBL_workouts : TextView? = null

    private var signOutButton: ImageView? = null
    private val topBarViewModel = TopBarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            heartCount = it.getInt(HEART_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val parent: View = inflater.inflate(R.layout.fragment_top_bar, container, false)
        findViews(parent)
        setClickListeners()
        setObservers()
        return parent
    }

    private fun setObservers() {
        topBarViewModel.allHeartFromLastSevenDays.observe(viewLifecycleOwner, {
            if (it != null)
                heartCountText?.text = it.toString()
        })
        topBarViewModel.totalCaloriesBurnedFromLastSevenDays.observe(viewLifecycleOwner, {
            if (it != null)
                top_bar_LBL_calories?.text = it.toString()
        })
        topBarViewModel.totalTimeFromLastSevenDays.observe(viewLifecycleOwner, {
            if (it != null){
                top_bar_LBL_hours?.text = topBarViewModel.calculateHoursFromSeconds(it)
            }
        })
        topBarViewModel.totalWorkoutsFromPastSevenDays.observe(viewLifecycleOwner,{
            if (it != null)
                top_bar_LBL_workouts?.text = it.toString()
        })

    }


    private fun findViews(parent:View){
        heartCountText = parent.findViewById(R.id.top_bar_LBL_heart_count)
        signOutButton = parent.findViewById(R.id.top_bar_BTN_sign_out)
        top_bar_LBL_calories = parent.findViewById(R.id.top_bar_LBL_calories)
        top_bar_LBL_hours = parent.findViewById(R.id.top_bar_LBL_hours)
        top_bar_LBL_workouts = parent.findViewById(R.id.top_bar_LBL_workouts)
    }


    private fun setClickListeners(){
        signOutButton!!.setOnClickListener {

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