package com.example.priscilla.nearbyplaces.library

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class DownloadUrl {
    // TODO: Add Google API Key
    private var key = "API-KEY"

    @Throws(IOException::class)
    fun readUrl(strUrl: String): String {
        var data = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL(strUrl)

            // Creating an http connection to communicate with url
            urlConnection = url.openConnection() as HttpURLConnection

            // Connecting to url
            urlConnection.connect()

            // Reading data from url
            iStream = urlConnection.inputStream

            data = convertStreamToString(iStream)

        } catch (e: Exception) {
            Log.d("Exception", e.toString())
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return data
    }

    private fun convertStreamToString(inputStream: InputStream): String {
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var allstring = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null)
                    allstring += line
            } while (line != null)
            bufferReader.close()
        } catch (ex: Exception) {
        }


        return allstring
    }

    fun getNearbyDownloadUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {
        val googlePlacesUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlacesUrl.append("keyword=$nearbyPlace")
        googlePlacesUrl.append("&rankby=distance")
        googlePlacesUrl.append("&location=$latitude,$longitude")
        googlePlacesUrl.append("&sensor=true")
        googlePlacesUrl.append("&key=$key")
        return googlePlacesUrl.toString()
    }

    fun getDirectionsUrl(currentPlace: LatLng, destination: LatLng): String {
        val googlePlacesUrl = StringBuilder("https://maps.googleapis.com/maps/api/directions/json?")
        googlePlacesUrl.append("origin=${currentPlace.latitude},${currentPlace.longitude}")
        googlePlacesUrl.append("&destination=${destination.latitude},${destination.longitude}")
        googlePlacesUrl.append("&mode=walking")
        googlePlacesUrl.append("&key=$key")
        return googlePlacesUrl.toString()
    }
}
