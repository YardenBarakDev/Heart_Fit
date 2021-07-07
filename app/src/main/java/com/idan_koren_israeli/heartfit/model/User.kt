package com.idan_koren_israeli.heartfit.model

import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

class User(val uid:String?=null, val name:String? = null, val weight:Float? = null, val hearts:Int?=null) : Serializable {

}