package com.example.divipay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RateUsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)

        // Find the button by its ID
        val rateUsButton: Button = findViewById(R.id.btn_rate_us)

        // Set an OnClickListener on the button
        rateUsButton.setOnClickListener {
            // Display a toast message to the user
            Toast.makeText(this, "Opening Google Play Store...", Toast.LENGTH_SHORT).show()

            // Get the package name of your app
            val packageName = applicationContext.packageName

            // Create a Uri to open the Play Store page for your app
            val uri = Uri.parse("market://details?id=$packageName")
            val playStoreIntent = Intent(Intent.ACTION_VIEW, uri)

            // Start the intent to open the Play Store
            startActivity(playStoreIntent)
        }
    }
}
