package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.mvvm.model.EquipmentSelect
import com.idan_koren_israeli.heartfit.recycler.adapter.EquipmentSelectAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentEquipmentSelect.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentEquipmentSelect : Fragment() {

    lateinit var parent: View
    lateinit var equipmentRecycler: RecyclerView
    lateinit var equipmentAdapter: EquipmentSelectAdapter
    private lateinit var finishButton: ImageButton
    lateinit var onSelectionDone: (equipmentSelected:Set<EquipmentSelect>) -> Unit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        parent = inflater.inflate(R.layout.fragment_equipment_select, container, false)
        findViews()
        initRecycler()
        initFinishButton()
        return parent
    }

    private fun findViews(){
        equipmentRecycler = parent.findViewById(R.id.equipment_select_RCV_recycler)
        finishButton = parent.findViewById(R.id.equipment_select_BTN_done)


    }

    fun initFinishButton(){
        finishButton.setOnClickListener {
            onSelectionDone(equipmentAdapter.getSelectedEquipment())
        }
    }

    private fun initRecycler(){
        val data = arrayListOf<EquipmentSelect>()

        // This data should be loaded from device.
        // There is a type of class in kotlin called 'data class'
        // it might be usable here.
        //DUBELES_LIGHT
        //"Dumbbells Light"

        data.add(EquipmentSelect(1,"Light\nDumbbells", R.drawable.ic_dumbbell_small))
        data.add(EquipmentSelect(2,"Medium\nDumbbells", R.drawable.ic_dumbbell))
        data.add(EquipmentSelect(3,"Heavy\nDumbbells", R.drawable.ic_dumbbell_large))
        data.add(EquipmentSelect(4,"Mattress", R.drawable.ic_mattress_full))
        data.add(EquipmentSelect(5,"Rope", R.drawable.ic_skipping_rope))
        data.add(EquipmentSelect(6,"Pull Up Bar", R.drawable.ic_pull_up))
        data.add(EquipmentSelect(7,"Light\nKettlebell", R.drawable.ic_kettlebell_small))
        data.add(EquipmentSelect(8,"Medium\nKettlebell", R.drawable.ic_kettlebell))
        data.add(EquipmentSelect(9,"Heavy\nKettlebell", R.drawable.ic_kettlebell_large))


        equipmentAdapter = EquipmentSelectAdapter(requireContext(), data)
        equipmentRecycler.layoutManager = GridLayoutManager(requireContext(), 3)

        equipmentRecycler.adapter = equipmentAdapter




    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EquipmentSelectFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FragmentEquipmentSelect().apply {
                    arguments = Bundle().apply {
                    }
                }
    }






}