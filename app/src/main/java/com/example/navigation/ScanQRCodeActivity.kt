package com.example.navigation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.navigation.databinding.QrcodeActivityBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class ScanQRCodeActivity: AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            showCamera()
        } else {
            Toast.makeText(this, "Camera permission is required to scan QR codes", Toast.LENGTH_SHORT).show()
        }
    }

    private val scanLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult -> run {
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            setResult(result.contents)
        }
    }
    }

    private fun setResult(string: String) {
        // Menampilkan hasil di TextView
        binding.textResult.text = string

        // Cek apakah hasil pemindaian adalah URL
        if (string.startsWith("http://") || string.startsWith("https://")) {
            // Buat intent untuk membuka URL di browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(string))
            startActivity(browserIntent)
        } else {
            // Jika hasil bukan URL, tampilkan pesan di Toast
            Toast.makeText(this, "Hasil pemindaian: $string", Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var binding: QrcodeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViews()

        binding.buttonGoToBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    private fun initViews() {
        checkPermissionCamera(this)
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(context, "Camera permission required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun initBinding() {
        binding = QrcodeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)
    }
}
