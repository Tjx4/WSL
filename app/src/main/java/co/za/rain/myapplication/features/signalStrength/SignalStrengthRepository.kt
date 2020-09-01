package co.za.rain.myapplication.features.signalStrength

import co.za.rain.myapplication.database.WSLDatabase
import co.za.rain.myapplication.database.tables.signalStats.SignalStatsTable

class SignalStrengthRepository(var database: WSLDatabase) {
    suspend fun addStatsToDb(stats: SignalStatsTable) {
       database.SIGSDAO.insert(stats)
    }

    suspend fun getStats(stats: SignalStatsTable): List<SignalStatsTable>? {
       return database.SIGSDAO.getStats()
    }
}