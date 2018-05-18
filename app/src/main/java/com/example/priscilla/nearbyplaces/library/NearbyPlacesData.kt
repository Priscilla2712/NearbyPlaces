package com.example.priscilla.nearbyplaces.library

import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class NearbyPlacesData : AsyncTask<Any, String, String>() {

    private lateinit var googlePlacesData: String
    private lateinit var mMap: GoogleMap
    private lateinit var url: String

    override fun doInBackground(vararg params: Any): String {
        mMap = params[0] as GoogleMap
        url = params[1] as String
        try {
            googlePlacesData = DownloadUrl().readUrl(url)
        } catch (e: Exception) {
            Log.d("GooglePlacesReadTask", e.toString())
        }
        return googlePlacesData
    }

    override fun onPostExecute(result: String) {
        var nearbyPlacesList: List<HashMap<String, String>>
        val dataParser = Parser()
        val jsonArray = dataParser.parse(result, "results")
        nearbyPlacesList = dataParser.getPlaces(jsonArray)
        showNearbyPlaces(nearbyPlacesList)
    }

    // Add markers and details for each place found.
    private fun showNearbyPlaces(nearbyPlacesList: List<HashMap<String, String>>) {
        for (i in 0 until nearbyPlacesList.size) {
            val markerOptions = MarkerOptions()
            val googlePlace = nearbyPlacesList[i]
            val lat = java.lang.Double.parseDouble(googlePlace["lat"])
            val lng = java.lang.Double.parseDouble(googlePlace["lng"])
            val placeName = googlePlace["place_name"]
            val vicinity = googlePlace["vicinity"]
            val latLng = LatLng(lat, lng)
            markerOptions.position(latLng).title("$placeName").snippet("$vicinity")
            mMap.addMarker(markerOptions)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11f))
        }
    }
}
