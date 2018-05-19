package com.example.priscilla.nearbyplaces.library

import com.google.android.gms.maps.model.LatLng
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DownloadUrlTest {
    private var key: String = ""
    @Before
    fun setUp() {
        // TODO: API KEY
        key = "API-KEY"
    }

    @After
    fun tearDown() {
        key = ""
    }

    @Test
    fun getNearbyDownloadUrl() {
        val downloadURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=farmácia&rankby=distance&location=-23.5233194,-46.7011391&sensor=true&key=$key"
        val urlResult = DownloadUrl().getNearbyDownloadUrl(-23.5233194, -46.7011391, "farmácia")
        assertEquals(downloadURL, urlResult)
    }

    @Test
    fun getDirectionsUrl() {
        val downloadURL = "https://maps.googleapis.com/maps/api/directions/json?origin=-23.5233213,-46.7011919&destination=-23.5250018,-46.700968&mode=walking&key=$key"
        val origin = LatLng(-23.5233213, -46.7011919)
        val destination = LatLng(-23.5250018, -46.700968)
        val urlResult = DownloadUrl().getDirectionsUrl(origin, destination)
        assertEquals(downloadURL, urlResult)
    }
}