//package com.example.divipay
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.MenuItem
//import android.widget.ImageButton
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.Toolbar
//import androidx.core.view.GravityCompat
//import androidx.drawerlayout.widget.DrawerLayout
//import com.google.android.material.navigation.NavigationView
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//
//// Add all necessary imports for your Activities
//import com.example.divipay.AddExpenseActivity
//import com.example.divipay.AnalysisActivity
//import com.example.divipay.ExpenseListActivity
//import com.example.divipay.ProfileSetupActivity
//
//class MainDashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
//
//    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var toolbar: Toolbar
//    private lateinit var fabAddItem: FloatingActionButton
//    private lateinit var incomeAmountTextView: TextView
//    private lateinit var expenseAmountTextView: TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main_dashboard)
//
//        // Initialize views using findViewById
//        drawerLayout = findViewById(R.id.drawer_layout)
//        toolbar = findViewById(R.id.toolbar)
//        fabAddItem = findViewById(R.id.fab_add_item)
//        incomeAmountTextView = findViewById(R.id.income_amount)
//        expenseAmountTextView = findViewById(R.id.expense_amount)
//
//        // Setting up the toolbar and navigation drawer toggle
//        setSupportActionBar(toolbar)
//        supportActionBar?.title = "Home" // Set a proper title
//
//        val toggle = ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            toolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        // Setup listeners for FAB and bottom bar icons
//        setupListeners()
//
//        // Update dashboard with initial data
//        updateDashboardData()
//
//        // The NavigationView is commented out in your XML, but this code is ready for it.
//        // Uncomment the NavigationView in activity_main_dashboard.xml to enable this functionality.
//        val navigationView: NavigationView? = findViewById(R.id.nav_view)
//        navigationView?.setNavigationItemSelectedListener(this)
//    }
//
//    private fun setupListeners() {
//        // Floating Action Button to add an expense
//        fabAddItem.setOnClickListener {
//            val intent = Intent(this, AddExpenseActivity::class.java)
//            startActivity(intent)
//        }
//
//        // Bottom navigation bar icons
//        findViewById<ImageButton>(R.id.icon_home).setOnClickListener {
//            // Home button clicked, no need to navigate, just show a toast
//            Toast.makeText(this, "You are on the Home screen", Toast.LENGTH_SHORT).show()
//        }
//        findViewById<ImageButton>(R.id.icon_groups).setOnClickListener {
//            val intent = Intent(this, AddGroupActivity::class.java)
//            startActivity(intent)
//        }
//        findViewById<ImageButton>(R.id.icon_add_user).setOnClickListener {
//            val intent = Intent(this, ProfileSetupActivity::class.java)
//            startActivity(intent)
//        }
//        findViewById<ImageButton>(R.id.icon_list).setOnClickListener {
//            val intent = Intent(this, ExpenseListActivity::class.java)
//            startActivity(intent)
//        }
//        findViewById<ImageButton>(R.id.icon_chart).setOnClickListener {
//            val intent = Intent(this, AnalysisActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    // Function to simulate updating dashboard data.
//    // In a real app, this data would come from a database or API.
//    private fun updateDashboardData() {
//        // Example placeholder data
//        val income = 15000.00
//        val expense = 8500.00
//
//        // Set the amounts with currency formatting
//        incomeAmountTextView.text = "₹ %.2f".format(income)
//        expenseAmountTextView.text = "₹ %.2f".format(expense)
//
//        // Note: For the "Group Expenses" and "Recent Personal Expenses" lists,
//        // you would typically use a RecyclerView and an Adapter to dynamically
//        // populate the list items from a data source. The current XML layout
//        // has hardcoded TextViews for demonstration purposes.
//    }
//
//    // Handles the back button press to close the drawer first
//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }
//
//    // Handles clicks on the navigation drawer menu items
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            // Uncomment the following lines when you enable the NavigationView in XML
//            // R.id.nav_home -> { Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show() }
//            // R.id.nav_expense -> { Toast.makeText(this, "Expenses selected", Toast.LENGTH_SHORT).show() }
//            // R.id.nav_analysis -> { Toast.makeText(this, "Analysis selected", Toast.LENGTH_SHORT).show() }
//            // R.id.nav_settings -> { Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show() }
//        }
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }
//}





package com.example.divipay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import com.example.divipay.* // Ensure all activity classes are imported

// Add all necessary imports for your Activities
import com.example.divipay.AddExpenseActivity
import com.example.divipay.AnalysisActivity
import com.example.divipay.ExpenseListActivity
import com.example.divipay.ProfileSetupActivity

class MainDashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var fabAddItem: FloatingActionButton
    private lateinit var incomeAmountTextView: TextView
    private lateinit var expenseAmountTextView: TextView
    private lateinit var welcomeText: TextView
    private lateinit var profileCircle: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_dashboard)

        // Initialize views using findViewById
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        fabAddItem = findViewById(R.id.fab_add_item)
        incomeAmountTextView = findViewById(R.id.income_amount)
        expenseAmountTextView = findViewById(R.id.expense_amount)
        welcomeText = findViewById(R.id.welcome_text)
        profileCircle = findViewById(R.id.profile_circle)

        // Setting up the toolbar and navigation drawer toggle
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home" // Set a proper title

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Setup listeners for FAB and bottom bar icons
        setupListeners()

        // Load and update the dashboard data
        updateDashboardData()
        loadUserProfile()

        // The NavigationView is commented out in your XML, but this code is ready for it.
//        val navigationView: NavigationView? = findViewById(R.id.nav_view)
//        navigationView?.setNavigationItemSelectedListener(this)
    }

    private fun loadUserProfile() {
        val sharedPref = getSharedPreferences("DiviPayUser", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", "User") // Default to "User" if not found
        val avatarId = sharedPref.getInt("userAvatarId", R.drawable.ic_profile_placeholder) // Default placeholder

        // Set the user's name
        welcomeText.text = "Welcome, $userName!"

        // Set the user's avatar
        profileCircle.setImageResource(avatarId)
    }

    private fun setupListeners() {
        // Floating Action Button to add an expense
        fabAddItem.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // Bottom navigation bar icons
        findViewById<ImageButton>(R.id.icon_home).setOnClickListener {
            // Home button clicked, no need to navigate, just show a toast
            Toast.makeText(this, "You are on the Home screen", Toast.LENGTH_SHORT).show()
        }
        findViewById<ImageButton>(R.id.icon_groups).setOnClickListener {
            val intent = Intent(this, AddGroupActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.icon_add_user).setOnClickListener {
            val intent = Intent(this, ProfileSetupActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.icon_list).setOnClickListener {
            val intent = Intent(this, ExpenseListActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.icon_chart).setOnClickListener {
            val intent = Intent(this, AnalysisActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to simulate updating dashboard data.
    private fun updateDashboardData() {
        // Example placeholder data
        val income = 15000.00
        val expense = 8500.00

        // Set the amounts with currency formatting
        incomeAmountTextView.text = "₹ %.2f".format(income)
        expenseAmountTextView.text = "₹ %.2f".format(expense)
    }

    // Handles the back button press to close the drawer first
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // Handles clicks on the navigation drawer menu items
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Start the MainDashboard Activity
                val intent = Intent(this, MainDashboard::class.java)
                startActivity(intent)
            }

            R.id.nav_groups -> {
                // Start the AddGroupActivity
                val intent = Intent(this, AddGroupActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_expense -> {
                // Start the ExpenseListActivity
                val intent = Intent(this, ExpenseListActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_analysis -> {
                // Start the AnalysisActivity
                val intent = Intent(this, AnalysisActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_settings -> {
                // Start the SettingsActivity
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
//

        }
        // Close the navigation drawer after an item is selected
        drawerLayout.closeDrawer(GravityCompat.START)
        return true // Return true to display the item as selected
    }
}





