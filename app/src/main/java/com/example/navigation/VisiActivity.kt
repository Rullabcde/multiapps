package com.example.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class VisiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visi)
        supportActionBar?.hide()

        val back: ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        val buttonGoBack: Button = findViewById(R.id.buttonGoToBack)
        buttonGoBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        val buttonOpenWebsite: Button = findViewById(R.id.buttonOpenWebsite)

        buttonOpenWebsite.setOnClickListener {
            val url = "https://www.smk2-yk.sch.id/berita/adiwiyata-smk-n-2-yogyakarta-mendapat-bantuan-tree-planting-dari-pt-komatsu-indonesia"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}
