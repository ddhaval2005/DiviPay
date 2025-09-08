//package com.example.divipay
//
//import android.os.Bundle
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.Toolbar
//import androidx.core.content.ContextCompat
//
//class SettingsActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_settings)
//
//        // Set up the Toolbar
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Settings"
//
//        // Setup the UI and click listeners for each menu item
//
//        setupMenuItem(
//            findViewById(R.id.menu_user_info),
//            R.drawable.ic_person, // The icon you need for User Info
//            "User Info"
//        )
//        setupMenuItem(
//            findViewById(R.id.menu_contact_us),
//            R.drawable.ic_phone,
//            "Contact Us"
//        )
//        setupMenuItem(
//            findViewById(R.id.menu_about_us),
//            R.drawable.ic_info,
//            "About Us"
//        )
//        setupMenuItem(
//            findViewById(R.id.menu_rate_us),
//            R.drawable.ic_star,
//            "Rate Us"
//        )
//        setupMenuItem(
//            findViewById(R.id.menu_faqs),
//            R.drawable.ic_help_center,
//            "FAQs"
//        )
//        setupMenuItem(
//            findViewById(R.id.menu_logout),
//            R.drawable.ic_logout,
//            "Logout",
//            R.color.logout_red // Use a color resource for better management
//        )
//
//        // Set up click listeners
//        findViewById<LinearLayout>(R.id.menu_user_info).setOnClickListener {
//            Toast.makeText(this, "User Info clicked", Toast.LENGTH_SHORT).show()
//        }
//        findViewById<LinearLayout>(R.id.menu_contact_us).setOnClickListener {
//            Toast.makeText(this, "Contact Us clicked", Toast.LENGTH_SHORT).show()
//        }
//        findViewById<LinearLayout>(R.id.menu_about_us).setOnClickListener {
//            Toast.makeText(this, "About Us clicked", Toast.LENGTH_SHORT).show()
//        }
//        findViewById<LinearLayout>(R.id.menu_rate_us).setOnClickListener {
//            Toast.makeText(this, "Rate Us clicked", Toast.LENGTH_SHORT).show()
//        }
//        findViewById<LinearLayout>(R.id.menu_faqs).setOnClickListener {
//            Toast.makeText(this, "FAQs clicked", Toast.LENGTH_SHORT).show()
//        }
//        findViewById<LinearLayout>(R.id.menu_logout).setOnClickListener {
//            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
//            // Implement logout logic here
//        }
//    }
//
//    // Helper function to set the icon and text for a menu item
//    private fun setupMenuItem(view: LinearLayout, iconResId: Int, title: String, colorResId: Int? = null) {
//        val icon = view.findViewById<ImageView>(R.id.iv_icon)
//        val titleText = view.findViewById<TextView>(R.id.tv_title)
//
//        icon.setImageResource(iconResId)
//        titleText.text = title
//
//        if (colorResId != null) {
//            val color = ContextCompat.getColor(this, colorResId)
//            icon.setColorFilter(color)
//            titleText.setTextColor(color)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }



//}












package com.example.divipay

import android.os.Bundle
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
            R.drawable.ic_person, // The icon you need for User Info
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
            R.color.logout_red // Use a color resource for better management
        )

        // Set up click listeners
        findViewById<LinearLayout>(R.id.menu_user_info).setOnClickListener {
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.menu_contact_us).setOnClickListener {
            Toast.makeText(this, "Contact Us clicked", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.menu_about_us).setOnClickListener {
            Toast.makeText(this, "About Us clicked", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.menu_rate_us).setOnClickListener {
            Toast.makeText(this, "Rate Us clicked", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.menu_faqs).setOnClickListener {
            Toast.makeText(this, "FAQs clicked", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.menu_logout).setOnClickListener {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
            // Implement logout logic here
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
