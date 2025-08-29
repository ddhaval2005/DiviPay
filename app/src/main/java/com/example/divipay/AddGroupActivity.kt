package com.example.divipay

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AddGroupActivity : AppCompatActivity() {

    private lateinit var etGroupName: EditText
    private lateinit var etMemberCount: EditText
    private lateinit var etTotalAmount: EditText
    private lateinit var btnCreateGroup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Group"

        // Initialize UI elements
        etGroupName = findViewById(R.id.etGroupName)
        etMemberCount = findViewById(R.id.etMemberCount)
        etTotalAmount = findViewById(R.id.etTotalAmount)
        btnCreateGroup = findViewById(R.id.btnCreateGroup)

        // Set up button click listener
        btnCreateGroup.setOnClickListener {
            val groupName = etGroupName.text.toString().trim()
            val memberCountStr = etMemberCount.text.toString().trim()
            val totalAmountStr = etTotalAmount.text.toString().trim()

            if (groupName.isEmpty() || memberCountStr.isEmpty() || totalAmountStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // You can add logic to save the group here
            Toast.makeText(this, "Group '$groupName' created!", Toast.LENGTH_LONG).show()

            finish() // Close the activity
        }

        // Setup bottom bar listeners
        setupBottomBarListeners()
    }

    private fun setupBottomBarListeners() {
        findViewById<ImageButton>(R.id.icon_home).setOnClickListener {
            val intent = Intent(this, MainDashboard::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.icon_groups).setOnClickListener {
            Toast.makeText(this, "You are already on the Add Group page!", Toast.LENGTH_SHORT).show()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}