package com.idan_koren_israeli.heartfit.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [ExerciseSummary::class])
abstract class HeartFitRoomDB : RoomDatabase() {

    abstract fun exerciseSummaryDao(): ExerciseSummaryDao


    companion object{
        private lateinit var instance: HeartFitRoomDB

        fun getInstance(context : Context) : HeartFitRoomDB{
            if (instance == null)
                instance =
                    Room.databaseBuilder(context, HeartFitRoomDB::class.java, "heartfit_db").build()

            return instance
        }

    }
}
