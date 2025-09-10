package com.example.divipay

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

// Data class to represent an expense. This should be placed outside of any Activity class.
data class Expense(
    val title: String,
    val amount: Double,
    val category: String,
    val date: String,
    val time: String,
    val paidBy: String,
    val groupId: String?
)

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var etExpenseTitle: EditText
    private lateinit var etExpenseAmount: EditText
    private lateinit var etPaidBy: EditText
    private lateinit var etCategory: EditText
    private lateinit var btnAddExpense: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Corrected: Removed back button logic from here
        supportActionBar?.title = "Add Expense"

        // Initialize UI elements
        etExpenseTitle = findViewById(R.id.etExpenseTitle)
        etExpenseAmount = findViewById(R.id.etExpenseAmount)
        etPaidBy = findViewById(R.id.etPaidBy)
        etCategory = findViewById(R.id.etCategory)
        btnAddExpense = findViewById(R.id.btnAddExpense)

        // Set up button click listener
        btnAddExpense.setOnClickListener {
            val expenseTitle = etExpenseTitle.text.toString().trim()
            val expenseAmountStr = etExpenseAmount.text.toString().trim()
            val paidBy = etPaidBy.text.toString().trim()
            val category = etCategory.text.toString().trim()

            if (expenseTitle.isEmpty() || expenseAmountStr.isEmpty() || paidBy.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expenseAmount = expenseAmountStr.toDoubleOrNull()
            if (expenseAmount == null) {
                Toast.makeText(this, "Invalid amount entered", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get current date and time
            val currentDate = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date())
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

            // Create an Expense object and save it
            val newExpense = Expense(
                title = expenseTitle,
                amount = expenseAmount,
                category = category,
                date = currentDate,
                time = currentTime,
                paidBy = paidBy,
                groupId = null
            )
            saveExpense(newExpense)

            Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show()

            // Close the activity and return to the expense list
            finish()
        }
    }

    private fun saveExpense(expense: Expense) {
        val sharedPref = getSharedPreferences("DiviPayExpenses", Context.MODE_PRIVATE)
        val gson = Gson()
        val existingExpensesJson = sharedPref.getString("expensesList", null)
        val type = object : TypeToken<ArrayList<Expense>>() {}.type
        val expensesList: ArrayList<Expense> = if (existingExpensesJson != null) {
            gson.fromJson(existingExpensesJson, type)
        } else {
            ArrayList()
        }

        //change1
        expensesList.add(expense)
        val updatedExpensesJson = gson.toJson(expensesList)
        with(sharedPref.edit()) {
            putString("expensesList", updatedExpensesJson)
            apply()
        }
    }
}