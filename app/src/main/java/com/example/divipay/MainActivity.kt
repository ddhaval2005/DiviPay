package com.example.divipay // Your package name

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// This MainActivity is likely a placeholder from project creation.
// If your app's main flow (SplashScreen -> Login -> MainDashboard) does NOT use this activity,
// you can safely delete this entire file.
// If you intend to use it later, ensure it does NOT contain any duplicate class definitions
// (like the MainDashboard class that caused the previous error).
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If this activity is not part of your app's flow, it might not need a layout.
        // If you do have a layout named activity_main.xml and want it to display something
        // uncomment the line below.
        // setContentView(R.layout.activity_main)
    }
}