package co.za.rain.myapplication.database.tables.signalStats

import androidx.room.*

@Dao
interface SIGSDAO {
    @Insert
    fun insert(locationsTable: SignalStatsTable)

    @Query("SELECT * FROM sig_stats ORDER BY id DESC")
    fun getStats(): List<SignalStatsTable>?
}