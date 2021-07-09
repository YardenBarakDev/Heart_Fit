package com.idan_koren_israeli.heartfit.recycler.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.mvvm.model.EquipmentSelect

class EquipmentSelectHolder(itemView: View, private val onClick: (equipmentSelect:EquipmentSelect) -> Unit, private var selected:Boolean = false):RecyclerView.ViewHolder(itemView){

    private lateinit var label: TextView
    private lateinit var image: ImageView


    init {
        findViews(itemView)
        updateCardView()

    }

    private fun findViews(parent:View){
        label = parent.findViewById(R.id.equipment_card_LBL_label)
        image = parent.findViewById(R.id.equipment_card_IMG_image)

    }


    fun setEquipment(equipmentSelect: EquipmentSelect){
        label.text = equipmentSelect.displayName
        image.setImageDrawable(ContextCompat.getDrawable(itemView.context,equipmentSelect.imageId!!))

        itemView.setOnClickListener {
            onClick.invoke(equipmentSelect)

            selected = !selected
            updateCardView()
        }
    }


    // This will update how the card is shown based on if it is selected
    private fun updateCardView(){
        if(selected){
            label.setBackgroundColor(ContextCompat.getColor(itemView.context!!, R.color.equip_selected))
        }
        else{
            label.setBackgroundColor(ContextCompat.getColor(itemView.context!!, R.color.equip_non_selected))
        }
    }
}
