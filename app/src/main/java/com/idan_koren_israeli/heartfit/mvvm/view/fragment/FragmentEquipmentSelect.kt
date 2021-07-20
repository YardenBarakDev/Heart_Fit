package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.component.MySharedPreferences
import com.idan_koren_israeli.heartfit.databinding.FragmentEquipmentSelectBinding
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
import com.idan_koren_israeli.heartfit.recycler.adapter.EquipmentSelectAdapter

class FragmentEquipmentSelect : Fragment(R.layout.fragment_equipment_select) {


    private lateinit var binding: FragmentEquipmentSelectBinding
    private lateinit var equipmentAdapter: EquipmentSelectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEquipmentSelectBinding.bind(view)
        initRecycler()
        initFinishButton()
    }

    private fun initFinishButton(){
        binding.equipmentSelectBTNDone.setOnClickListener {
            val selectedArrayList :ArrayList<Equipment?> = ArrayList(equipmentAdapter.getSelectedEquipment())
            MySharedPreferences.saveArrayList(selectedArrayList,CommonUtils.KEY_EQUIPMENT)

            findNavController().navigate(R.id.action_fragmentEquipmentSelect_to_fragmentHome)
        }
    }

    private fun initRecycler(){
        val data = arrayListOf<Equipment>()

        for(eq : Equipment in Equipment.values())
            data.add(eq)

        equipmentAdapter = EquipmentSelectAdapter(requireContext(), data)
        binding.equipmentSelectRCVRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.equipmentSelectRCVRecycler.adapter = equipmentAdapter

    }
}