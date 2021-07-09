package com.idan_koren_israeli.heartfit.db.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [ExerciseSummary::class])
abstract class HeartFitRoomDB : RoomDatabase() {

    abstract fun exerciseSummaryDao(): ExerciseSummaryDao


    companion object{
        private var instance: HeartFitRoomDB? = null

        fun getInstance() : HeartFitRoomDB{return instance as HeartFitRoomDB}

        @Synchronized
        fun init(context : Context) : HeartFitRoomDB{
            if (instance == null)
                instance = Room.databaseBuilder(context, HeartFitRoomDB::class.java, "heartfit_db").build()
            return instance as HeartFitRoomDB
        }

    }
}
