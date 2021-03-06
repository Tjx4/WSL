package co.za.rain.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.rain.myapplication.R
import co.za.rain.myapplication.extensions.blinkView
import co.za.rain.myapplication.features.locationTracker.LocationTrackerActivity
import co.za.rain.myapplication.models.UserLocation

class LocationsAdapter(context: Context, private val userLocations: List<UserLocation>) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {
    private val locationTrackerActivity = context as LocationTrackerActivity
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var locationClickListener: LocationClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.location_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userLocation = userLocations[position]
        holder.locationNameTv.text = userLocation.name
        holder.locationDescriptionTv.text = userLocation.description
        holder.locationDateTimeTv.text = "You were here on ${userLocation.dateTime}"
        holder.moreBtn.setOnClickListener {
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                locationTrackerActivity.showMoreInfoOnLocation(position)
            })
        }
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var locationNameTv = itemView.findViewById<TextView>(R.id.tvLocationName)
        internal var locationDescriptionTv = itemView.findViewById<TextView>(R.id.tvLocationDescription)
        internal var locationDateTimeTv = itemView.findViewById<TextView>(R.id.tvLocationDateTime)
        internal var moreBtn = itemView.findViewById<TextView>(R.id.btnMore)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            locationClickListener?.onServiceCategoryClick(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): UserLocation? {
        return userLocations[id]
    }

    interface LocationClickListener {
        fun onServiceCategoryClick(view: View, position: Int)
    }

    fun setLocationClickListener(locationClickListener: LocationClickListener) {
        this.locationClickListener = locationClickListener
    }

    override fun getItemCount() = userLocations.size

}