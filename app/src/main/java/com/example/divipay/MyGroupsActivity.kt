package com.example.divipay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Data class to represent a group. This should be placed outside of any Activity class.
data class Group(
    val name: String,
    val memberCount: Int,
    val totalAmount: Double,
    val memberNames: ArrayList<String> // Added a list for member names
)

class MyGroupsActivity : AppCompatActivity() {

    private lateinit var groupsRecyclerView: RecyclerView
    private lateinit var tvNoGroups: TextView
    private lateinit var groupsAdapter: GroupsAdapter
    private var groupsList = ArrayList<Group>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_groups)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Groups"

        // Initialize UI elements
        groupsRecyclerView = findViewById(R.id.groupsRecyclerView)
        tvNoGroups = findViewById(R.id.tvNoGroups)

        setupBottomBarListeners()

        // Load the groups from SharedPreferences when the activity is created
        loadGroups()
    }

    override fun onResume() {
        super.onResume()
        // Reload groups to reflect any changes from AddGroupActivity
        loadGroups()
    }

    private fun loadGroups() {
        val sharedPref = getSharedPreferences("DiviPayGroups", Context.MODE_PRIVATE)
        val gson = Gson()
        val groupsJson = sharedPref.getString("groupsList", null)
        val type = object : TypeToken<ArrayList<Group>>() {}.type

        if (groupsJson != null) {
            groupsList = gson.fromJson(groupsJson, type)
            if (groupsList.isNotEmpty()) {
                tvNoGroups.visibility = View.GONE
                groupsRecyclerView.visibility = View.VISIBLE
                setupRecyclerView()
            } else {
                tvNoGroups.visibility = View.VISIBLE
                groupsRecyclerView.visibility = View.GONE
            }
        } else {
            tvNoGroups.visibility = View.VISIBLE
            groupsRecyclerView.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        groupsAdapter = GroupsAdapter(groupsList)
        groupsRecyclerView.layoutManager = LinearLayoutManager(this)
        groupsRecyclerView.adapter = groupsAdapter
    }

    private fun setupBottomBarListeners() {
        // This is a placeholder for your existing bottom bar logic
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

    // Handles the back button in the toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

class GroupsAdapter(private val groups: ArrayList<Group>) : RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>() {

    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val groupName: TextView = view.findViewById(R.id.tvGroupName)
        val memberCount: TextView = view.findViewById(R.id.tvMemberCount)
        val totalAmount: TextView = view.findViewById(R.id.tvTotalAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_list_item, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]
        holder.groupName.text = group.name
        holder.memberCount.text = "Members: ${group.memberCount}"
        holder.totalAmount.text = "Amount: ₹${"%.2f".format(group.totalAmount)}"

//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, AddExpenseActivity::class.java)
//            intent.putStringArrayListExtra("membersList", group.memberNames)
//            holder.itemView.context.startActivity(intent)
//        }
    }

    override fun getItemCount() = groups.size
}