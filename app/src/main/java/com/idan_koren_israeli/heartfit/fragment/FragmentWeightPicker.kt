package com.idan_koren_israeli.heartfit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.firebase.database.DatabaseManager
import com.shawnlin.numberpicker.NumberPicker
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWeightPicker.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentWeightPicker : Fragment() {


    private lateinit var okButton: Button
    private lateinit var leftNumber: NumberPicker // Num of KG s
    private lateinit var rightNumber: NumberPicker // Num of 100G s



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNumbers()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val parent:View =inflater.inflate(R.layout.fragment_weight_picker, container, false)
        findViews(parent)
        initOkButton()
        return parent
    }


    private fun findViews(parent: View){
        okButton = parent.findViewById(R.id.weight_picker_BTN_ok)
        leftNumber = parent.findViewById(R.id.weight_picker_NUM_left)
        rightNumber = parent.findViewById(R.id.weight_picker_NUM_right)

    }

    private fun initOkButton(){
        okButton.setOnClickListener {
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.HALF_UP

            val newWeight :Float = (leftNumber.value + (rightNumber.value * 0.1f))
            DatabaseManager.storeCurrentUserWeight(newWeight)
            //TODO store on ROOM SQL too
        }
    }

    private fun initNumbers(){
        //TODO oldWeight should be read from ROOM-SQL

        DatabaseManager.loadCurrentUser {
            var oldWeight = 80f
            if(it!=null)
                oldWeight = it.weight!!
            leftNumber.value = oldWeight.roundToInt()
            rightNumber.value = ((oldWeight - oldWeight.roundToInt()) * 10).roundToInt()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentWeightPicker.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentWeightPicker().apply {
                arguments = Bundle().apply {

                }
            }
    }
}