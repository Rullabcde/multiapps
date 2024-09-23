package com.example.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint

class MapActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))
        setContentView(R.layout.activity_map)
        supportActionBar?.hide()
        mapView = findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.controller.setZoom(10.0)
        mapView.controller.setCenter(GeoPoint(-7.7956, 110.3695)) // Jakarta coordinates

        val marker = Marker(mapView)
        marker.position = GeoPoint(-7.7956, 110.3695)
        marker.title = "Marker in Yogyakarta"
        mapView.overlays.add(marker)
    }
}
