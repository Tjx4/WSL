package co.za.rain.myapplication.database.tables.locations

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LOCDAO {
    @Insert
    fun insert(locationsTable: LocationsTable)

    @Update
    fun update(locationsTable: LocationsTable)

    @Delete
    fun delete(locationsTable: LocationsTable)

    @Query("SELECT * FROM locations WHERE id = :key")
    fun get(key: Long): LocationsTable?

    @Query("SELECT * FROM locations ORDER BY id DESC LIMIT 1")
    fun getLastLocation(): LocationsTable?

    @Query("SELECT * FROM locations ORDER BY id DESC")
    fun getAllLocationsLiveData(): LiveData<List<LocationsTable>>

    @Query("SELECT * FROM locations ORDER BY id DESC")
    fun getAllLocations(): List<LocationsTable>?

    @Query("DELETE  FROM locations")
    fun clear()
}