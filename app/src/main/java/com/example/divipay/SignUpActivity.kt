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

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val rootView = findViewById<android.view.View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        val usernameEditText: EditText = findViewById(R.id.etUsername)
        val passwordEditText: EditText = findViewById(R.id.etPassword)
        val confirmPasswordEditText: EditText = findViewById(R.id.etConfirmPassword)
        val signUpButtonLayout: LinearLayout = findViewById(R.id.btnSignUpLayout)
        val loginTextView: TextView = findViewById(R.id.tvLogin)

        // Set click listener for the sign-up button
        signUpButtonLayout.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Save the new user's credentials using SharedPreferences
                val sharedPref = getSharedPreferences("DiviPayUser", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("username", username)
                    putString("password", password)
                    apply()
                }

                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                val i = Intent(this, MainDashboard::class.java)
                startActivity(i)
                finish()
            }
        }

        // Set click listener for the "Already have an account?" text
        loginTextView.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish() // Finish this activity to prevent going back to it with the back button
        }
    }
}