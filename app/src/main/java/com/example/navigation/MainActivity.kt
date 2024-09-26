package com.example.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        val calculator: ImageView = findViewById(R.id.calculator)
        calculator.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        val scanqr: ImageView = findViewById(R.id.scanqr)
        scanqr.setOnClickListener {
            val intent = Intent(this, ScanQRCodeActivity::class.java)
            startActivity(intent)
        }

        val maps: ImageView = findViewById(R.id.maps)
        maps.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        val adiwiyata: ImageView = findViewById(R.id.adiwiyata)
        adiwiyata.setOnClickListener {
            val intent = Intent(this, VisiActivity::class.java)
            startActivity(intent)
        }
    }
}
