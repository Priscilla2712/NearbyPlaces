package com.example.priscilla.nearbyplaces.library

import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONArray


class DirectionsData : AsyncTask<Any, String, String>() {

    private lateinit var mMap: GoogleMap
    private lateinit var url: String
    private var polyline: Polyline? = null

    override fun doInBackground(vararg params: Any): String {
        var data = ""
        mMap = params[0] as GoogleMap
        url = params[1] as String
        try {
            // Fetching the data from web service
            data = DownloadUrl().readUrl(url)
        } catch (e: Exception) {
            Log.d("Background Task", e.toString())
        }

        return data
    }

    override fun onPostExecute(result: String) {
        val dataParser = Parser()
        val directions = dataParser.parse(result, "routes")
        drawPath(directions)
    }

    // Draw the Polyline using the route array obtained from parser.
    private fun drawPath(routeArray: JSONArray) {
        try {
            polyline?.remove()
            val routes = routeArray.getJSONObject(0)
            val overviewPolylines = routes
                    .getJSONObject("overview_polyline")
            val encodedString = overviewPolylines.getString("points")
            val list = Parser().decodePolyline(encodedString)
            val polylineOptions = PolylineOptions()
                    .width(10f).color(Color.BLUE).geodesic(true)
            for (z in 0 until list.size) {
                val point = list[z]
                polylineOptions.add(point)
            }
            polyline = mMap.addPolyline(polylineOptions)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
