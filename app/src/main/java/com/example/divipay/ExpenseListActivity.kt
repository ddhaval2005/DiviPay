package com.example.divipay

import android.content.Context
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class ExpenseListActivity : AppCompatActivity() {

    private lateinit var groupsRecyclerView: RecyclerView
    private lateinit var tvNoExpenses: TextView
    private lateinit var fabAddExpense: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Expenses"

        // Initialize views
        groupsRecyclerView = findViewById(R.id.rvExpenses)
        tvNoExpenses = findViewById(R.id.tvNoExpenses) // New TextView for no expenses message
        fabAddExpense = findViewById(R.id.fabAddExpense)

        // Setup RecyclerView
        groupsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the Floating Action Button
        fabAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // Setup bottom bar listeners
        setupBottomBarListeners()
    }

    override fun onResume() {
        super.onResume()
        // Load expenses every time the activity is resumed to reflect changes
        loadExpenses()
    }

    private fun loadExpenses() {
        val sharedPref = getSharedPreferences("DiviPayExpenses", Context.MODE_PRIVATE)
        val gson = Gson()
        val expensesJson = sharedPref.getString("expensesList", null)
        val type = object : TypeToken<ArrayList<Expense>>() {}.type

        if (expensesJson != null) {
            val expensesList: ArrayList<Expense> = gson.fromJson(expensesJson, type)
            if (expensesList.isNotEmpty()) {
                tvNoExpenses.visibility = View.GONE
                groupsRecyclerView.visibility = View.VISIBLE
                val adapter = ExpenseAdapter(expensesList)
                groupsRecyclerView.adapter = adapter
            } else {
                tvNoExpenses.visibility = View.VISIBLE
                groupsRecyclerView.visibility = View.GONE
            }
        } else {
            tvNoExpenses.visibility = View.VISIBLE
            groupsRecyclerView.visibility = View.GONE
        }
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



class ExpenseAdapter(private val expenses: List<Expense>) :
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
        holder.expenseDescription.text = expense.category
        holder.paidBy.text = "Paid by: ${expense.paidBy}"
        holder.expenseAmount.text = "₹%.2f".format(expense.amount)

        // Format the time for display
        val displayTime = try {
            val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val date = inputFormat.parse(expense.time)
            outputFormat.format(date)
        } catch (e: Exception) {
            expense.time
        }
        holder.expenseTime.text = displayTime

        // Set the icon and colors based on category
        val (iconRes, bgColor, tintColor) = getIconAndColorsForCategory(expense.category)
        holder.expenseIcon.setImageResource(iconRes)
        holder.iconBackground.setBackgroundColor(Color.parseColor(bgColor))
        holder.expenseIcon.setColorFilter(Color.parseColor(tintColor))
    }

    private fun getIconAndColorsForCategory(category: String): Triple<Int, String, String> {
        return when (category.lowercase()) {
            "house rent" -> Triple(R.drawable.ic_home, "#FDE2E2", "#F472B6")
            "electricity bill" -> Triple(R.drawable.ic_bill, "#D1FAE5", "#10B981")
            "taxi fare" -> Triple(R.drawable.ic_directions_car, "#FEF3C7", "#F59E0B")
            "groceries" -> Triple(R.drawable.ic_shopping_bag, "#EDE9FE", "#8B5CF6")
            "coffee" -> Triple(R.drawable.ic_more_horiz, "#FEE2E2", "#EF4444")
            else -> Triple(R.drawable.ic_more_horiz, "#FEE2E2", "#EF4444")
        }
    }

    override fun getItemCount() = expenses.size
}