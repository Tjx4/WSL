package co.za.rain.myapplication.features.base.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationManager
import android.os.*
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import co.za.rain.myapplication.R
import co.za.rain.myapplication.constants.USER_MARKER_TAG
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener

abstract class BaseMapActivity : BaseParentActivity(), OnMapReadyCallback,
    ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    protected var fragmentMap: SupportMapFragment? = null
    protected var googleMap: GoogleMap? = null
    protected var locationRequest: LocationRequest? = null
    protected var googleApiClient: GoogleApiClient? = null
    protected var airportMarkers: List<Marker>? = null
    protected var userMarker: Marker? = null
    protected val USER_ZOOM = 14

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWakeLock()
    }

    protected abstract fun onGpsOff()
    protected abstract fun onLocationPermissionDenied()
    protected fun setWakeLock() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    protected fun checkLocationPermissionAndContinue() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkGoogleApi()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in permissions.indices) {
            val permission = permissions[i]
            val grantResult = grantResults[i]

            if (permission == Manifest.permission.ACCESS_FINE_LOCATION) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    checkGoogleApi()
                } else {
                    onLocationPermissionDenied()
                }
            }
        }
    }

    protected fun checkGoogleApi() {
        val api = GoogleApiAvailability.getInstance()
        val isAv = api.isGooglePlayServicesAvailable(this)
        if (isAv == ConnectionResult.SUCCESS) {
            initMap()
        } else if (api.isUserResolvableError(isAv)) {
            var apiError = ""
            // NotificationHelper.showLongToast(this, resources.getString(R.string.api_user_error))
        } else {
            var apiError = ""
           // NotificationHelper.showLongToast(this, getResources().getString(R.string.api_error))
        }
    }

    protected abstract fun initMap()
    protected abstract fun mapReady(googleMap: GoogleMap?)

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        buildGoogleApiClient()
        this.googleMap = googleMap
        googleMap.isMyLocationEnabled = true
        moveLocationButtonToBottomRight()
        mapReady(googleMap)
    }

    protected fun moveLocationButtonToBottomRight() {
        val mapView = fragmentMap?.view
        if (mapView != null) {
            val childView = mapView.findViewById<View>("1".toInt())
            if (childView != null) {
                val locationButton = (childView.parent as View).findViewById<View>("2".toInt())
                val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                val bottomMargin = resources.getDimension(R.dimen.xlarge_view_margin).toInt()
                val rightMargin = resources.getDimension(R.dimen.large_view_margin).toInt()
                layoutParams.setMargins(0, 0, rightMargin, bottomMargin)
                locationButton.layoutParams = layoutParams
            }
        }
    }

    private fun moveInToLocation(ll: LatLng, zoom: Int) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, zoom.toFloat())
        googleMap!!.moveCamera(cameraUpdate)
    }

    private fun zoomInToLocation(ll: LatLng, zoom: Int, animate: Boolean) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, zoom.toFloat())
        googleMap!!.animateCamera(cameraUpdate)
    }

    protected fun goToLocationZoomNoAnimation(ll: LatLng, zoom: Int) {
        moveInToLocation(ll, zoom)
    }

    protected fun goToLocationZoomAnimated(ll: LatLng, zoom: Int) {
        zoomInToLocation(ll, zoom, true)
    }

    protected fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        googleApiClient?.connect()
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(bundle: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest!!.interval = 2000
        locationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationCallback: LocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    if (locationResult == null) {
                        return
                    }
                    for (location in locationResult.locations) {
                        onLocationChanged(location)
                    }
                }
            }
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener(this,
                OnSuccessListener<Location?> { location ->
                    if (location != null) {
                        onRequestListenerSuccess(location)
                        fusedLocationClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.myLooper()
                        )
                    }
                })
        } else {
            checkLocationPermissionAndContinue()
        }
    }

    protected abstract fun onRequestListenerSuccess(location: Location?)
    override fun onConnectionSuspended(i: Int) {
        // NotificationHelper.showShortToast(this,resources.getString(R.string.connection_suspended_message))
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
       // NotificationHelper.showShortToast(this, getResources().getString(R.string.api_user_error))
    }

    protected fun moveUserMarker(latLng: LatLng) {
        moveMarker(latLng, userMarker)
    }

    private fun moveMarker(toLatLong: LatLng, currentMarker: Marker?) {
        animateMarkerMovement(currentMarker, toLatLong, false)
    }

    fun animateMarkerMovement(marker: Marker?, toPosition: LatLng, hideMarker: Boolean) {
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val proj = googleMap!!.projection
        val startPoint = proj.toScreenLocation(marker!!.position)
        val startLatLng = proj.fromScreenLocation(startPoint)
        val duration: Long = 900
        val interpolator: Interpolator = LinearInterpolator()
        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - start
                val t = interpolator.getInterpolation(elapsed.toFloat() / duration)
                val lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude
                val lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude
                marker.setPosition(LatLng(lat, lng))
                if (t < 1.0) {
// Post again 16ms later.
                    handler.postDelayed(this, 16)
                } else {
                    if (hideMarker) {
                        marker.isVisible = false
                    } else {
                        marker.isVisible = true
                    }
                }
            }
        })
    }

    protected fun getMarker(
        latLng: LatLng?,
        title: String?,
        snippet: String?,
        tag: String?
    ): Marker {
        val markerOptions = MarkerOptions()
            .title(title)
            .flat(true)
            .anchor(0.5f, 0.5f)
            .rotation(0f)
            .position(latLng!!)
        if (snippet != null && !snippet.isEmpty()) {
            markerOptions.snippet(snippet)
        }
        val airportMarker = googleMap!!.addMarker(markerOptions)
        airportMarker.tag = tag
        return airportMarker
    }

    protected open fun isGPSOn(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lm = this.getSystemService(LOCATION_SERVICE) as LocationManager
            lm.isLocationEnabled
        } else {
            val mode = Settings.Secure.getInt(
                this.contentResolver, Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            )
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    protected fun getBitmapDescriptor(@DrawableRes id: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    open fun plotUserMarker(latLng: LatLng?, title: String?, snippet: String?) {
        val userMarker = getMarker(latLng, title, snippet, USER_MARKER_TAG)
       // val markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.client_marker)
        //userMarker.setIcon(markerIcon)
        this.userMarker = userMarker
    }
}