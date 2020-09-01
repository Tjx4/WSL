package co.za.rain.myapplication.database.tables.locations

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "locations")
data class LocationsTable (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0L,

    @ColumnInfo(name = "name")
    var name:String? = null,

    @ColumnInfo(name = "description")
    var description:String? = null,

    @ColumnInfo(name = "coordinates")
    var coordinates:String? = null,

    @ColumnInfo(name = "dateTime")
    var dateTime:String? = null,

): Parcelable