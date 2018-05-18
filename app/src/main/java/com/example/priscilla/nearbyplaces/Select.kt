package com.example.priscilla.nearbyplaces

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.priscilla.nearbyplaces.NearbyPlaces
import com.example.priscilla.nearbyplaces.RouteNearbyPlaces
import com.example.priscilla.nearbyplaces.R

class Select : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        val nearbyButton = findViewById<Button>(R.id.nearbyButton)
        val routeButton = findViewById<Button>(R.id.routeButton)
        nearbyButton.setOnClickListener{
            val intent = Intent(this, NearbyPlaces::class.java)
            startActivity(intent)
        }
        routeButton.setOnClickListener{
            val intent = Intent(this, RouteNearbyPlaces::class.java)
            startActivity(intent)
        }

    }

}
