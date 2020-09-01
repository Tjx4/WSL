package co.za.rain.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.za.rain.myapplication.database.tables.locations.LocationsTable
import co.za.rain.myapplication.database.tables.locations.LOCDAO
import co.za.rain.myapplication.database.tables.signalStats.SIGSDAO
import co.za.rain.myapplication.database.tables.signalStats.SignalStatsTable

@Database(entities = [LocationsTable::class, SignalStatsTable::class], version = 1, exportSchema = false)
abstract class WSLDatabase : RoomDatabase() {
    abstract val LOCDAO: LOCDAO
    abstract val SIGSDAO: SIGSDAO

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