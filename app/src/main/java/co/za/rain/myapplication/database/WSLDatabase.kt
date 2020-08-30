package co.za.rain.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.za.dvt.myskilldevapp.features.database.tables.LocationsTable
import co.za.rain.myapplication.database.tables.LOCDAO

@Database(entities = [LocationsTable::class], version = 1, exportSchema = false)
abstract class WSLDatabase : RoomDatabase() {
    abstract val LOCDAO: LOCDAO

    companion object{
        @Volatile
        private var INSTANCE: WSLDatabase? = null

        fun getInstance(context: Context): WSLDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, WSLDatabase::class.java, "wsl_database")
                                    .fallbackToDestructiveMigration()
                                    .build()
                    INSTANCE = instance
                }

                return  instance
            }
        }
    }

}