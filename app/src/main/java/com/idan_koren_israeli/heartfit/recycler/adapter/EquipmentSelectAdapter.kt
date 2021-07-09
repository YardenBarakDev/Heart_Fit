package com.idan_koren_israeli.heartfit.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.mvvm.model.EquipmentSelect
import com.idan_koren_israeli.heartfit.recycler.holder.EquipmentSelectHolder

class EquipmentSelectAdapter(context: Context, equipmentSelectData: ArrayList<EquipmentSelect>): RecyclerView.Adapter<EquipmentSelectHolder>() {
    var data:ArrayList<EquipmentSelect> = arrayListOf()
    var mInflater:LayoutInflater? = null

    private var selectedEquipmentSelect: MutableSet<EquipmentSelect> = mutableSetOf()

    init{
        mInflater = LayoutInflater.from(context)
        data.addAll(equipmentSelectData)
    }


    private fun onEquipmentClick(equipmentSelect:EquipmentSelect){
        if(equipmentSelect in selectedEquipmentSelect) {
            selectedEquipmentSelect.remove(equipmentSelect)
            Log.i("pttt", "REMOVED SELECTION " + equipmentSelect.displayName)
        }
        else {
            selectedEquipmentSelect.add(equipmentSelect)
            Log.i("pttt", "ADDED SELECTION " + equipmentSelect.displayName)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentSelectHolder {
        val view:View = mInflater!!.inflate(R.layout.holder_equipment_select,parent, false)

        return EquipmentSelectHolder(view ,{equipment -> onEquipmentClick(equipment)})
    }

    override fun onBindViewHolder(holder: EquipmentSelectHolder, position: Int) {
        holder.setEquipment(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * This function will return all the equipment that is currently selected from the list
     * This is the output of the recyclerview
     */
    fun getSelectedEquipment():Set<EquipmentSelect>{
        return selectedEquipmentSelect
    }

}