package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.component.MySharedPreferences
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
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

    private fun initFinishButton(){
        finishButton.setOnClickListener {
            val selectedArrayList :ArrayList<Equipment?> = ArrayList(equipmentAdapter.getSelectedEquipment())
            MySharedPreferences.saveArrayList(selectedArrayList,CommonUtils.KEY_EQUIPMENT)

            findNavController().navigate(R.id.action_fragmentEquipmentSelect_to_fragmentHome)
        }
    }

    private fun initRecycler(){
        val data = arrayListOf<Equipment>()

        for(eq : Equipment in Equipment.values()){
            data.add(eq)
        }

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