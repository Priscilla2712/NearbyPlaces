package com.example.priscilla.nearbyplaces

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class NearbyPlaces : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearbyplaces)
        val searchButton = findViewById<Button>(R.id.button)
        searchButton.setOnClickListener {
            val searchString = findViewById<EditText>(R.id.searchText).getText()
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("searchText", searchString.toString())
            startActivity(intent)
        }

    }

}
