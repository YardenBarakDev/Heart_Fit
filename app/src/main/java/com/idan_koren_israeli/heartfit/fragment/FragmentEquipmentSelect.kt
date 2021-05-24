package com.idan_koren_israeli.heartfit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.model.Equipment
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        parent = inflater.inflate(R.layout.fragment_equipment_select, container, false)
        findViews()
        initRecycler()
        return parent
    }

    private fun findViews(){
        equipmentRecycler = parent.findViewById(R.id.equipment_select_RCV_recycler)
    }

    private fun initRecycler(){
        val data = arrayListOf<Equipment>()

        // This data should be loaded from device.
        // There is a type of class in kotlin called 'data class'
        // it might be usable here.
        data.add(Equipment(1,"Dumbbells 1", R.drawable.ic_dumbbell))
        data.add(Equipment(2,"Dumbbells 2", R.drawable.ic_dumbbell))
        data.add(Equipment(3,"Dumbbells 3", R.drawable.ic_dumbbell))
        data.add(Equipment(4,"Dumbbells 4", R.drawable.ic_dumbbell))


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