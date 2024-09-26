package com.example.navigation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MapActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar?.hide()

        val back: ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // Inisialisasi WebView
        webView = findViewById(R.id.webViewMap)

        // Mengatur pengaturan WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.setSupportZoom(true) // Mengaktifkan zoom
        webSettings.builtInZoomControls = true // Kontrol zoom yang dibangun

        // Inisialisasi FusedLocationProviderClient untuk mendapatkan lokasi pengguna
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Cek izin lokasi
        checkLocationPermission()
    }

    // Fungsi untuk cek izin lokasi
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Jika izin belum diberikan, minta izin akses lokasi
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
        } else {
            // Jika izin sudah diberikan, dapatkan lokasi saat ini
            getLastKnownLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                // Jika lokasi ditemukan, muat peta Google dengan lokasi saat ini
                val latitude = location.latitude
                val longitude = location.longitude
                val url = "https://maps.google.com/maps?q=$latitude,$longitude"
                webView.loadUrl(url)
            } else {
                Log.d("MapActivity", "Location not found")
                // Tangani kasus di mana lokasi tidak ditemukan
                // Misalnya, tampilkan pesan atau muat peta default
            }
        }.addOnFailureListener { e ->
            Log.e("MapActivity", "Failed to get location: ${e.message}")
            // Tangani kesalahan saat mendapatkan lokasi
        }
    }

    // Untuk menangani hasil dari permintaan izin
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Jika izin diberikan, dapatkan lokasi
                getLastKnownLocation()
            } else {
                // Jika izin ditolak, beri tahu pengguna
                Log.d("MapActivity", "Location permission denied")
                // Bisa menambahkan dialog atau UI untuk memberikan info kepada pengguna
            }
        }
    }
}
