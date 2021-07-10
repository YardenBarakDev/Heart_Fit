package com.idan_koren_israeli.heartfit.mvvm.view_model

import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.mvvm.model.User

object CurrentUserDataViewModel {

    // caching the current user
    private var currentUser: User? = null

    fun loadHearts(onLoaded: (value: Int?) -> Unit){
        DatabaseManager.loadCurrentUser {
            if(it==null)
                onLoaded.invoke(null)
            else
                onLoaded.invoke(it.hearts)
        }
    }

    @Throws(Exception::class)
    fun storeHearts(newValue: Int){
        if(currentUser ==null)
            throw Exception("Current user is not loaded") // User not loaded yet

        currentUser!!.hearts = newValue
        DatabaseManager.storeUser(currentUser!!)
    }

    fun loadWeight(onLoaded: (value: Float?) -> Unit){
        DatabaseManager.loadCurrentUser {
            if(it==null)
                onLoaded.invoke(null)
            else
                onLoaded.invoke(it.weight)
        }
    }

    @Throws(Exception::class)
    fun storeWeight(newValue: Float){
        if(currentUser ==null)
            throw Exception("Current user is not loaded") // User not loaded yet

        currentUser!!.weight = newValue
        DatabaseManager.storeUser(currentUser!!)
    }

    fun loadCurrentUser(onLoaded: (value: User?) -> Unit){
        if(currentUser !=null)
            return onLoaded.invoke(currentUser)
        else
            DatabaseManager.loadCurrentUser{
                currentUser = it // Saving currently loaded as cache
                onLoaded.invoke(it)
            }
    }



}