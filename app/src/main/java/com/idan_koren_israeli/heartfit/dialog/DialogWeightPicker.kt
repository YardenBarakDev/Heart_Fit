package com.idan_koren_israeli.heartfit.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.firebase.database.DatabaseManager
import com.shawnlin.numberpicker.NumberPicker
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 * Use the [DialogWeightPicker.newInstance] factory method to
 * create an instance of this fragment.
 */
class DialogWeightPicker {


    private lateinit var parent: View
    private lateinit var leftNumber: NumberPicker // Num of KG s
    private lateinit var rightNumber: NumberPicker // Num of 100G s


     fun inflateLayout(inflater: LayoutInflater): View? {
        parent =inflater.inflate(R.layout.alert_weight_picker, null, false)
        return parent
    }

    fun showDialog(materialAlertDialogBuilder: MaterialAlertDialogBuilder) {
        findViews(parent)
        initNumbers()

        // Building the Alert dialog using materialAlertDialogBuilder instance
        materialAlertDialogBuilder.setView(parent)
            .setTitle("Select Your Weight")
            .setMessage("What is your current weight? This will help us estimate how many calories you burn")
            .setPositiveButton("Ok") { dialog, _ ->
                val df = DecimalFormat("#.#")
                df.roundingMode = RoundingMode.HALF_UP
                val newWeight :Float = (leftNumber.value + (rightNumber.value * 0.1f))
                DatabaseManager.storeCurrentUserWeight(newWeight)
                dialog.dismiss()

                //TODO store on ROOM SQL/SP too
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }





    private fun findViews(parent: View){
        leftNumber = parent.findViewById(R.id.weight_picker_NUM_left)
        rightNumber = parent.findViewById(R.id.weight_picker_NUM_right)

    }


    private fun initNumbers(){
        //TODO oldWeight should be read from ROOM-SQL/SP

        DatabaseManager.loadCurrentUserWeight{
            var weight = 80f
            if(it!=null)
                weight = it
            leftNumber.value = weight.roundToInt()
            rightNumber.value = ((weight - weight.roundToInt()) * 10).roundToInt()
        }

    }

}