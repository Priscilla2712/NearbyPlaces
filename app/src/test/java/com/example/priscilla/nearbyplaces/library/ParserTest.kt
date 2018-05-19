package com.example.priscilla.nearbyplaces.library

import org.junit.Test

import org.junit.Assert.*

class ParserTest {
    @Test
    fun decodePolyline() {
        var encodedLine = """lnqnCpi`|GClCc@C}B?a@BwAGgCYyD_@MXMXV\"""
        val polyline = "[lat/lng: (-23.52375,-46.70121), lat/lng: (-23.52373,-46.70192), lat/lng: (-23.52355,-46.7019), lat/lng: (-23.52292,-46.7019), lat/lng: (-23.52275,-46.70192), lat/lng: (-23.52231,-46.70188), lat/lng: (-23.52163,-46.70175), lat/lng: (-23.5207,-46.70159), lat/lng: (-23.52063,-46.70172), lat/lng: (-23.52056,-46.70185), lat/lng: (-23.52068,-46.702)]"
        val polyResult = Parser().decodePolyline(encodedLine)
        assertEquals(polyline, polyResult.toString())
    }
}