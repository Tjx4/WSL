package co.za.rain.myapplication.database.tables.signalStats

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "sig_stats")
data class SignalStatsTable (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0L,

    @ColumnInfo(name = "strength")
    var strength:String? = null,

    @ColumnInfo(name = "dateTime")
    var dateTime:String? = null
): Parcelable