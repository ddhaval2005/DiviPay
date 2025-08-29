package com.example.divipay

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout // Import LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import de.hdodenhof.circleimageview.CircleImageView

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var etDescription: EditText
    private lateinit var actCategory: AutoCompleteTextView
    private lateinit var etPrice: EditText
    private lateinit var actPaidBy: AutoCompleteTextView
    private lateinit var btnSplitEqually: Button
    private lateinit var btnSplitUnequally: Button
    private lateinit var btnSplitItemWise: Button
    private lateinit var btnAddBillImage: Button
    private lateinit var btnAddMoreBills: Button
    private lateinit var btnSubmitExpense: LinearLayout // Corrected type to LinearLayout
    private lateinit var btnAddFriends: ImageButton
    private lateinit var ivYouAvatar: de.hdodenhof.circleimageview.CircleImageView

    private val categories = arrayOf("Food", "Travel", "Utilities", "Rent", "Shopping", "Misc.")
    private val paidByOptions = arrayOf("You", "Friend 1", "Friend 2") // Dynamically populate with actual members

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Initialize Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Expense"

        // Initialize UI elements
        etDescription = findViewById(R.id.etDescription)
        actCategory = findViewById(R.id.actCategory)
        etPrice = findViewById(R.id.etPrice)
        actPaidBy = findViewById(R.id.actPaidBy)
        btnSplitEqually = findViewById(R.id.btnSplitEqually)
        btnSplitUnequally = findViewById(R.id.btnSplitUnequally)
        btnSplitItemWise = findViewById(R.id.btnSplitItemWise)
        btnAddBillImage = findViewById(R.id.btnAddBillImage)
        btnAddMoreBills = findViewById(R.id.btnAddMoreBills)
        btnSubmitExpense = findViewById(R.id.btnSubmitExpenseLayout) // Corrected ID
        btnAddFriends = findViewById(R.id.btnAddFriends)
        ivYouAvatar = findViewById(R.id.ivYouAvatar)

        // Set up Category Dropdown
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        actCategory.setAdapter(categoryAdapter)

        // Set up Paid By Dropdown
        val paidByAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, paidByOptions)
        actPaidBy.setAdapter(paidByAdapter)

        // Set up listeners
        btnAddFriends.setOnClickListener {
            Toast.makeText(this, "Add Friends clicked", Toast.LENGTH_SHORT).show()
        }

        btnSplitEqually.setOnClickListener {
            Toast.makeText(this, "Split Equally selected", Toast.LENGTH_SHORT).show()
        }
        btnSplitUnequally.setOnClickListener {
            Toast.makeText(this, "Split Unequally selected", Toast.LENGTH_SHORT).show()
        }
        btnSplitItemWise.setOnClickListener {
            Toast.makeText(this, "Split Item Wise selected", Toast.LENGTH_SHORT).show()
        }

        btnAddBillImage.setOnClickListener {
            Toast.makeText(this, "Add Bill Image clicked", Toast.LENGTH_SHORT).show()
        }

        btnAddMoreBills.setOnClickListener {
            Toast.makeText(this, "Add More Bills clicked", Toast.LENGTH_SHORT).show()
        }

        // The listener is now on the LinearLayout
        btnSubmitExpense.setOnClickListener {
            validateAndSubmitExpense()
        }
    }

    private fun validateAndSubmitExpense() {
        val description = etDescription.text.toString().trim()
        val priceStr = etPrice.text.toString().trim()
        val category = actCategory.text.toString().trim()
        val paidBy = actPaidBy.text.toString().trim()

        if (description.isEmpty()) {
            etDescription.error = "Description is required"
            return
        }
        if (priceStr.isEmpty()) {
            etPrice.error = "Price is required"
            return
        }
        val price = priceStr.toDoubleOrNull()
        if (price == null || price <= 0) {
            etPrice.error = "Enter a valid price"
            return
        }
        if (category.isEmpty()) {
            actCategory.error = "Category is required"
            return
        }
        if (paidBy.isEmpty()) {
            actPaidBy.error = "Paid By is required"
            return
        }

        Toast.makeText(this, "Expense submitted: $description, ₹$price, Category: $category, Paid by: $paidBy", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}