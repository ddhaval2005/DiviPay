package com.example.divipay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ContactUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Contact Us"

        // Set up click listeners for the contact details

        findViewById<LinearLayout>(R.id.email_container).setOnClickListener {
            val email = findViewById<TextView>(R.id.tv_email_address).text.toString()
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // Only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from DiviPay App")
            startActivity(Intent.createChooser(intent, "Send Email"))
        }

        findViewById<LinearLayout>(R.id.phone_container).setOnClickListener {
            val phone = findViewById<TextView>(R.id.tv_phone_number).text.toString()
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
