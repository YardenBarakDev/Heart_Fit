package com.idan_koren_israeli.heartfit.model.exercise

import com.idan_koren_israeli.heartfit.model.EquipmentSelect
import com.idan_koren_israeli.heartfit.model.MuscleGroup

open class Exercise(val id: Int? = null, val name: String? = null,
                    val muscles: Set<MuscleGroup>? = null, val equipmentSelects: Set<EquipmentSelect>? = null){


}
