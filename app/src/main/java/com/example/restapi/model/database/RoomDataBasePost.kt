package com.example.restapi.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PostDao::class], version = 1, exportSchema = false)
abstract class RoomDataBasePost : RoomDatabase() {

    abstract fun getPostDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDataBasePost? = null

        fun getDatabase(context: Context): RoomDataBasePost {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBasePost::class.java,
                    "post_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


// otra formula para lo mismo

// fun getDatabase(context: Context):PhotoDataRoomBase{
//            if(INSTANCE == null)
//                synchronized(this){
//                    INSTANCE = Room.databaseBuilder(context.applicationContext, PhotoDataRoomBase::class.java, DBNAME).build()
//                }
//            return INSTANCE!!
//        }

