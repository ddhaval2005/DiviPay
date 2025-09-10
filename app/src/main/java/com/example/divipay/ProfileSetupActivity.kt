package com.example.divipay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.hdodenhof.circleimageview.CircleImageView

// Data class to represent a group member
data class Member(
    val name: String,
    val avatarResId: Int,
    val email: String
)

class ProfileSetupActivity : AppCompatActivity() {

    private var selectedAvatar: CircleImageView? = null
    private var selectedAvatarId: Int = 0

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnAddMember: Button
    private lateinit var btnDone: Button
    private lateinit var membersRecyclerView: RecyclerView
    private lateinit var membersAdapter: MembersAdapter
    private var membersList = ArrayList<Member>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Members"

        // Initialize views from the layout
        val avatarMale1: CircleImageView = findViewById(R.id.avatar_male1)
        val avatarFemale1: CircleImageView = findViewById(R.id.avatar_female1)
        btnAddMember = findViewById(R.id.btnAddMember)
        btnDone = findViewById(R.id.btnDone)
        etFullName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        membersRecyclerView = findViewById(R.id.membersRecyclerView)

        // Set up click listeners for each avatar
        avatarFemale1.setOnClickListener { selectAvatar(avatarFemale1, R.drawable.avatar_female1) }
        avatarMale1.setOnClickListener { selectAvatar(avatarMale1, R.drawable.avatar_male1) }

        // Set up the RecyclerView with a lambda for the delete action
        membersAdapter = MembersAdapter(membersList) { member ->
            removeMember(member)
        }
        membersRecyclerView.layoutManager = LinearLayoutManager(this)
        membersRecyclerView.adapter = membersAdapter

        // Load existing members from SharedPreferences
        loadMembers()

        btnAddMember.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (selectedAvatar != null && fullName.isNotEmpty() && email.isNotEmpty()) {
                // Add the new member and save the updated list
                val newMember = Member(fullName, selectedAvatarId, email)
                membersList.add(newMember)
                saveMembers()
                membersAdapter.notifyItemInserted(membersList.size - 1)

                Toast.makeText(this, "$fullName added!", Toast.LENGTH_SHORT).show()

                // Clear the form for the next member
                clearForm()
            } else {
                Toast.makeText(
                    this,
                    "Please select an avatar and enter a name and email.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnDone.setOnClickListener {
            Toast.makeText(this, "Finished adding members!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
            finish()
        }

        // Setup listeners for the bottom bar buttons
        setupBottomBarListeners()
    }

    private fun loadMembers() {
        val sharedPref = getSharedPreferences("DiviPayMembers", Context.MODE_PRIVATE)
        val gson = Gson()
        val existingMembersJson = sharedPref.getString("membersList", null)
        val type = object : TypeToken<ArrayList<Member>>() {}.type

        if (existingMembersJson != null) {
            membersList = gson.fromJson(existingMembersJson, type)
            membersAdapter.membersList = membersList
            membersAdapter.notifyDataSetChanged()
        }
    }

    private fun saveMembers() {
        val sharedPref = getSharedPreferences("DiviPayMembers", Context.MODE_PRIVATE)
        val gson = Gson()
        val updatedMembersJson = gson.toJson(membersList)
        with(sharedPref.edit()) {
            putString("membersList", updatedMembersJson)
            apply()
        }
    }

    private fun removeMember(member: Member) {
        val position = membersList.indexOf(member)
        if (position != -1) {
            membersList.removeAt(position)
            saveMembers()
            membersAdapter.notifyItemRemoved(position)
            Toast.makeText(this, "${member.name} removed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearForm() {
        etFullName.text.clear()
        etEmail.text.clear()
        selectedAvatar?.let {
            it.borderWidth = 2
            it.borderColor = ContextCompat.getColor(this, android.R.color.transparent)
        }
        selectedAvatar = null
        selectedAvatarId = 0
    }

    private fun selectAvatar(avatar: CircleImageView, avatarId: Int) {
        selectedAvatar?.let {
            it.borderWidth = 2
            it.borderColor = ContextCompat.getColor(this, android.R.color.transparent)
        }
        selectedAvatar = avatar
        selectedAvatarId = avatarId
        avatar.borderWidth = 4
        avatar.borderColor = ContextCompat.getColor(this, R.color.drawer_menu_item_selected)
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
            Toast.makeText(this, "You are already on the Profile Setup page!", Toast.LENGTH_SHORT)
                .show()
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

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
    }

    // RecyclerView Adapter for members
    class MembersAdapter(
        var membersList: ArrayList<Member>,
        private val onMemberDeleted: (Member) -> Unit
    ) : RecyclerView.Adapter<MembersAdapter.MemberViewHolder>() {

        class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val memberName: TextView = view.findViewById(R.id.tvMemberName)
            val memberAvatar: CircleImageView = view.findViewById(R.id.ivMemberAvatar)
            val deleteButton: ImageButton = view.findViewById(R.id.btnDeleteMember)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
            return MemberViewHolder(view)
        }

        override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
            val member = membersList[position]
            holder.memberName.text = member.name
            holder.memberAvatar.setImageResource(member.avatarResId)

            holder.deleteButton.setOnClickListener {
                onMemberDeleted(member)
            }
        }

        override fun getItemCount() = membersList.size
    }
}