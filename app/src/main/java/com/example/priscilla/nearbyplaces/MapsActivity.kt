package com.example.priscilla.nearbyplaces

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.priscilla.nearbyplaces.library.DirectionsData
import com.example.priscilla.nearbyplaces.library.DownloadUrl
import com.example.priscilla.nearbyplaces.library.NearbyPlacesData
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    override fun onMarkerClick(p0: Marker?) = false
    private lateinit var mMap: GoogleMap
    private lateinit var searchText: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    companion object {
        private const val PLACE_PICKER_REQUEST = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        searchText = intent.extras.getString("searchText")
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            loadPlacePicker()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                placeMarkerOnMap(place)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        mMap.setOnInfoWindowClickListener(this)
        setUpMap()
    }

    override fun onInfoWindowClick(marker: Marker?) {
        getRoute(marker)
    }

    // Place marker and add info to it
    private fun placeMarkerOnMap(place: Place) {
        // 1
        val markerOptions = MarkerOptions().position(place.latLng).title(place.name.toString()).snippet(place.address.toString())
        // 2
        val mark = mMap.addMarker(markerOptions)
        mark.showInfoWindow()

    }

    // Load place picker for more searches
    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()
        try {
            startActivityForResult(builder.build(this@MapsActivity), PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    // Load map with nearby places
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null) {
                    lastLocation = location
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                    searchNearbyPlaces()
                }
            }
        }
    }

    // Get the url and makes the request to directions endpoint.
    private fun getRoute(marker: Marker?) {
        val currentLatLang = LatLng(lastLocation.latitude, lastLocation.longitude)
        val url = DownloadUrl().getDirectionsUrl(currentLatLang, marker?.position!!)
        DirectionsData().execute(mMap, url)
    }

    // Get the url and makes the request to nearby places endpoint.
    private fun searchNearbyPlaces() {
        val url = DownloadUrl().getNearbyDownloadUrl(lastLocation.latitude, lastLocation.longitude, searchText)
        NearbyPlacesData().execute(mMap, url)
    }

}

