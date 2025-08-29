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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class AnalysisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Chart"

        // --- CHART SETUP ---
        val expenseChart: PieChart = findViewById(R.id.expenseChart)
        setupPieChart(expenseChart)
        // --- END CHART SETUP ---

        // Setup RecyclerView for expense categories
        val recyclerView: RecyclerView = findViewById(R.id.rvExpenseCategories)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Dummy data for the expense categories list based on your image
        val expenseCategories = listOf(
            ExpenseCategory("House", "25% of expenses", "₹2,125.00", R.drawable.ic_home, "#FCE7F3", "#EC4899"),
            ExpenseCategory("Bills", "30% of expenses", "₹2,550.00", R.drawable.ic_bill, "#D1FAE5", "#10B981"),
            ExpenseCategory("Transport", "15% of expenses", "₹1,275.00", R.drawable.ic_directions_car, "#FEF3C7", "#F59E0B"),
            ExpenseCategory("Shopping", "20% of expenses", "₹1,700.00", R.drawable.ic_shopping_bag, "#EDE9FE", "#8B5CF6"),
            ExpenseCategory("Other", "10% of expenses", "₹850.00", R.drawable.ic_more_horiz, "#FEE2E2", "#EF4444")
        )

        val adapter = ExpenseCategoryAdapter(expenseCategories)
        recyclerView.adapter = adapter

        // --- Bottom Bar Listeners ---
        setupBottomBarListeners()
        // --- End Bottom Bar Listeners ---
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

    private fun setupPieChart(pieChart: PieChart) {
        pieChart.setUsePercentValues(false)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 75f
        pieChart.transparentCircleRadius = 80f
        pieChart.setDrawCenterText(false)
        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = false
        pieChart.isHighlightPerTapEnabled = true
        pieChart.legend.isEnabled = false

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(25f, ""))
        entries.add(PieEntry(30f, ""))
        entries.add(PieEntry(15f, ""))
        entries.add(PieEntry(20f, ""))
        entries.add(PieEntry(10f, ""))

        val dataSet = PieDataSet(entries, "Expense Categories")
        dataSet.sliceSpace = 2f
        dataSet.selectionShift = 5f

        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#F472B6"))
        colors.add(Color.parseColor("#34D399"))
        colors.add(Color.parseColor("#FBBF24"))
        colors.add(Color.parseColor("#A78BFA"))
        colors.add(Color.parseColor("#F87171"))
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(false)
        pieChart.data = data
        pieChart.invalidate()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

// This data class is crucial for the code to work correctly.
data class ExpenseCategory(
    val name: String,
    val percentage: String,
    val amount: String,
    val icon: Int,
    val backgroundColor: String,
    val iconTint: String
)

// This RecyclerView adapter is also required for the list to display content.
class ExpenseCategoryAdapter(private val categories: List<ExpenseCategory>) :
    RecyclerView.Adapter<ExpenseCategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconBackground: LinearLayout = view.findViewById(R.id.iconBackground)
        val categoryIcon: ImageView = view.findViewById(R.id.ivCategoryIcon)
        val categoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val categoryPercentage: TextView = view.findViewById(R.id.tvCategoryPercentage)
        val categoryAmount: TextView = view.findViewById(R.id.tvCategoryAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.name
        holder.categoryPercentage.text = category.percentage
        holder.categoryAmount.text = category.amount
        holder.categoryIcon.setImageResource(category.icon)

        holder.iconBackground.setBackgroundColor(Color.parseColor(category.backgroundColor))
        holder.categoryIcon.setColorFilter(Color.parseColor(category.iconTint))
    }

    override fun getItemCount() = categories.size
}