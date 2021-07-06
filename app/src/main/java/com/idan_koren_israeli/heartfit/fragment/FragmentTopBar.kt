package com.idan_koren_israeli.heartfit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.activity.MainActivity
import com.idan_koren_israeli.heartfit.component.WorkoutCycleManager
import com.idan_koren_israeli.heartfit.firebase.auth.AuthManager

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
            AuthManager.signOut(requireActivity() as MainActivity)
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
                    putInt(HEART_COUNT, WorkoutCycleManager.getHearts())
                }
            }
    }
}