package com.idan_koren_israeli.heartfit.mvvm.model

import java.io.Serializable

const val DEFAULT_WEIGHT : Float = 80f
const val DEFAULT_HEARTS : Int = 0

class User(val uid:String?=null, var name:String? = null, var weight:Float? = DEFAULT_WEIGHT, var hearts:Int?=DEFAULT_HEARTS) : Serializable {

}