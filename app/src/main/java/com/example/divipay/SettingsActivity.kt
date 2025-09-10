package com.example.divipay

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"

        // Setup the UI and click listeners for each menu item
        setupMenuItem(
            findViewById(R.id.menu_user_info),
            R.drawable.ic_person,
            "User Info"
        )
        setupMenuItem(
            findViewById(R.id.menu_contact_us),
            R.drawable.ic_phone,
            "Contact Us"
        )
        setupMenuItem(
            findViewById(R.id.menu_about_us),
            R.drawable.ic_info,
            "About Us"
        )
        setupMenuItem(
            findViewById(R.id.menu_rate_us),
            R.drawable.ic_star,
            "Rate Us"
        )
        setupMenuItem(
            findViewById(R.id.menu_faqs),
            R.drawable.ic_help_center,
            "FAQs"
        )
        setupMenuItem(
            findViewById(R.id.menu_logout),
            R.drawable.ic_logout,
            "Logout",
            R.color.logout_red
        )

        findViewById<LinearLayout>(R.id.menu_user_info).setOnClickListener {
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.menu_contact_us).setOnClickListener {
            val intent = Intent(this, ContactUsActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.menu_about_us).setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.menu_rate_us).setOnClickListener {
            val intent = Intent(this, RateUsActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.menu_faqs).setOnClickListener {
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.menu_logout).setOnClickListener {
            // Implement logout logic with a confirmation dialog
            AlertDialog.Builder(this)
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { dialog, which ->
                    // User confirmed, show toast and navigate to LoginActivity
                    Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    finish() // Finish the current activity
                }
                .setNegativeButton("No") { dialog, which ->
                    // User canceled, dismiss the dialog
                    dialog.dismiss()
                }
                .show()
        }
    }

    // Helper function to set the icon and text for a menu item
    private fun setupMenuItem(view: LinearLayout, iconResId: Int, title: String, colorResId: Int? = null) {
        val icon = view.findViewById<ImageView>(R.id.iv_icon)
        val titleText = view.findViewById<TextView>(R.id.tv_title)

        icon.setImageResource(iconResId)
        titleText.text = title

        if (colorResId != null) {
            val color = ContextCompat.getColor(this, colorResId)
            icon.setColorFilter(color)
            titleText.setTextColor(color)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
