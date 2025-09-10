package com.example.divipay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val rootView = findViewById<android.view.View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        val usernameEditText: EditText = findViewById(R.id.etUsername)
        val passwordEditText: EditText = findViewById(R.id.etPassword)
        val loginButtonLayout: LinearLayout = findViewById(R.id.btnLoginLayout)
        val registerTextView: TextView = findViewById(R.id.tvRegister)

        registerTextView.setOnClickListener {
            // Create an Intent to navigate to the SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for the login button
        loginButtonLayout.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

             //Check if credentials are valid (using SharedPreferences for local storage)
            if (isCredentialsValid(username, password)) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                val i = Intent(this, MainDashboard::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }

        }

        // Set click listener for the "Register here" text
        registerTextView.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    // A helper function to check credentials against a simple local storage
    private fun isCredentialsValid(username: String, password: String): Boolean {
        val sharedPref = getSharedPreferences("DiviPayUser", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("username", "")
        val savedPassword = sharedPref.getString("password", "")

        return username == savedUsername && password == savedPassword
    }
}