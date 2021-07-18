package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager

private const val HEART_COUNT = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTopBar.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTopBar : Fragment() {
    private var heartCount: Int? = null

    private var heartCountText: TextView? = null
    private var signOutButton: ImageView? = null


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
        updateUI()
        return parent
    }



    private fun findViews(parent:View){
        heartCountText = parent.findViewById(R.id.top_bar_LBL_heart_count)
        signOutButton = parent.findViewById(R.id.top_bar_BTN_sign_out)
    }

    fun setHeartCount(newCount: Int){
        this.heartCount = newCount
        updateUI()
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

    private fun updateUI(){
        heartCountText!!.text = heartCount.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param heartCount Count of hearts.
         * @return A new instance of fragment FragmentTopBar.
         */
        @JvmStatic
        fun newInstance(heartCount: Int) =
            FragmentTopBar().apply {
                arguments = Bundle().apply {
                    putInt(HEART_COUNT, heartCount)
                }
            }

        @JvmStatic
        fun newInstance() =
            FragmentTopBar().apply {
                arguments = Bundle().apply {
                    putInt(HEART_COUNT, DatabaseManager.currentUser.hearts)
                }
            }
    }
}