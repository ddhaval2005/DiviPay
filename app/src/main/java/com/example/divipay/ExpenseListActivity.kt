package com.example.divipay

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExpenseListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Expenses"

        // Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.rvExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Dummy data for the expense list
        val expensesList = listOf(
            ExpenseItem("Tuesday, 23 July", "House Rent", "Rent for July", "Paid by: You", "₹2,125.00", "10:45 AM", R.drawable.ic_home, "#FDE2E2", "#F472B6"),
            ExpenseItem("Tuesday, 23 July", "Electricity Bill", "Monthly bill", "Paid by: John", "₹2,550.00", "09:30 AM", R.drawable.ic_bill, "#D1FAE5", "#10B981"),
            ExpenseItem("Monday, 22 July", "Taxi Fare", "Trip to office", "Paid by: You", "₹1,275.00", "05:15 PM", R.drawable.ic_directions_car, "#FEF3C7", "#F59E0B"),
            ExpenseItem("Sunday, 21 July", "Groceries", "Weekly shopping", "Paid by: Jane", "₹1,700.00", "02:00 PM", R.drawable.ic_shopping_bag, "#EDE9FE", "#8B5CF6"),
            ExpenseItem("Sunday, 21 July", "Coffee", "Morning coffee with friends", "Paid by: You", "₹850.00", "11:00 AM", R.drawable.ic_more_horiz, "#FEE2E2", "#EF4444")
        )

        val adapter = ExpenseAdapter(expensesList)
        recyclerView.adapter = adapter

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
            Toast.makeText(this, "You are already on the List page!", Toast.LENGTH_SHORT).show()
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

// Data class to represent an expense item
data class ExpenseItem(
    val date: String,
    val title: String,
    val description: String,
    val paidBy: String,
    val amount: String,
    val time: String,
    val icon: Int,
    val background: String,
    val tint: String
)

// RecyclerView Adapter for the expense list
class ExpenseAdapter(private val expenses: List<ExpenseItem>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expenseTitle: TextView = view.findViewById(R.id.tvExpenseTitle)
        val expenseDescription: TextView = view.findViewById(R.id.tvExpenseDescription)
        val paidBy: TextView = view.findViewById(R.id.tvPaidBy)
        val expenseAmount: TextView = view.findViewById(R.id.tvExpenseAmount)
        val expenseTime: TextView = view.findViewById(R.id.tvExpenseTime)
        val expenseIcon: ImageView = view.findViewById(R.id.ivExpenseIcon)
        val iconBackground: LinearLayout = view.findViewById(R.id.iconBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.expenseTitle.text = expense.title
        holder.expenseDescription.text = expense.description
        holder.paidBy.text = expense.paidBy
        holder.expenseAmount.text = expense.amount
        holder.expenseTime.text = expense.time
        holder.expenseIcon.setImageResource(expense.icon)
        holder.iconBackground.setBackgroundColor(Color.parseColor(expense.background))
        holder.expenseIcon.setColorFilter(Color.parseColor(expense.tint))
    }

    override fun getItemCount() = expenses.size
}