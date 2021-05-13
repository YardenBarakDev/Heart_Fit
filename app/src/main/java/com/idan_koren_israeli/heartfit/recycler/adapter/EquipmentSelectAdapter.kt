package com.idan_koren_israeli.heartfit.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Equipment
import com.idan_koren_israeli.heartfit.recycler.holder.EquipmentSelectHolder

class EquipmentSelectAdapter(context: Context, equipmentData: ArrayList<Equipment>): RecyclerView.Adapter<EquipmentSelectHolder>() {
    var data:ArrayList<Equipment> = arrayListOf()
    var mInflater:LayoutInflater? = null

    private var selectedEquipment: MutableSet<Equipment> = mutableSetOf()

    init{
        mInflater = LayoutInflater.from(context)
        data.addAll(equipmentData)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentSelectHolder {
        val view:View = mInflater!!.inflate(R.layout.holder_equipment_select,parent, false)

        return EquipmentSelectHolder(view ,{ equipment ->
            if(equipment in selectedEquipment) {
                selectedEquipment.remove(equipment)
                Log.i("pttt", "REMOVED SELECTION " + equipment.name)
            }
            else {
                selectedEquipment.add(equipment)
                Log.i("pttt", "ADDED SELECTION " + equipment.name)
            }
        })
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
    fun getSelectedEquipment():Set<Equipment>{
        return selectedEquipment
    }

}