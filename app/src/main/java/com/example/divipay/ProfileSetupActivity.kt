package com.example.divipay

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import de.hdodenhof.circleimageview.CircleImageView
import com.example.divipay.MainDashboard
import com.example.divipay.AnalysisActivity

class ProfileSetupActivity : AppCompatActivity() {

    private var selectedAvatar: CircleImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Setup"

        val avatarMale1: CircleImageView = findViewById(R.id.avatar_male1)
        val avatarFemale1: CircleImageView = findViewById(R.id.avatar_female1)
        val finalizeButton: Button = findViewById(R.id.btnFinalize)

        // Set up click listeners for each avatar
        avatarFemale1.setOnClickListener { selectAvatar(avatarFemale1) }
        avatarMale1.setOnClickListener { selectAvatar(avatarMale1) }

        finalizeButton.setOnClickListener {
            // Handle the finalize button click
            if (selectedAvatar != null) {
                Toast.makeText(this, "Profile finalized!", Toast.LENGTH_SHORT).show()
                // Add your logic to save the profile data here
                finish() // Close the activity
            } else {
                Toast.makeText(this, "Please select an avatar", Toast.LENGTH_SHORT).show()
            }
        }

        // Setup listeners for the bottom bar buttons
        setupBottomBarListeners()
    }

    private fun setupBottomBarListeners() {
        findViewById<ImageButton>(R.id.icon_home).setOnClickListener {
            val intent = Intent(this, MainDashboard::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.icon_groups).setOnClickListener {
            val intent = Intent(this, AddGroupActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.icon_add_user).setOnClickListener {
            val intent = Intent(this, ProfileSetupActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.icon_list).setOnClickListener {
            val intent = Intent(this, ExpenseListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.icon_chart).setOnClickListener {
            val intent = Intent(this, AnalysisActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
    }

    // Handle the back button in the toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun selectAvatar(avatar: CircleImageView) {
        selectedAvatar?.let {
            it.borderWidth = 2
            it.borderColor = ContextCompat.getColor(this, android.R.color.transparent)
        }

        selectedAvatar = avatar
        avatar.borderWidth = 4
        avatar.borderColor = ContextCompat.getColor(this, R.color.drawer_menu_item_selected)
    }
}