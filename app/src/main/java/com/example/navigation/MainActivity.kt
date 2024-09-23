package com.example.navigation


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // ImageButton 1 menuju ke SecondActivity
        val imageButton1: ImageView = findViewById(R.id.imageButton1)
        imageButton1.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // ImageButton 2 menuju ke Visi
        val imageButton2: ImageView = findViewById(R.id.imageButton2)
        imageButton2.setOnClickListener {
            val intent = Intent(this, VisiActivity::class.java)
            startActivity(intent)
        }

        val imageButton3: ImageView = findViewById(R.id.imageButton3)
        imageButton3.setOnClickListener {
            val intent = Intent(this, ScanQRCodeActivity::class.java)
            startActivity(intent)
        }

        val imageButton4: ImageView = findViewById(R.id.imageButton4)
        imageButton4.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}
