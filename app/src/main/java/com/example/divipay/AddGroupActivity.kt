package com.example.divipay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AddGroupActivity : AppCompatActivity() {

    private lateinit var etGroupName: EditText
    private lateinit var etMemberCount: EditText
    private lateinit var etTotalAmount: EditText
    private lateinit var btnCreateGroup: Button
    private lateinit var btnMyGroups: Button
    private lateinit var llMemberNames: LinearLayout

    private var currentMemberCount = 0

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
        btnMyGroups = findViewById(R.id.btnMyGroups)
        llMemberNames = findViewById(R.id.llMemberNames)

        // Set up TextWatcher for member count
        etMemberCount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateMemberNameFields(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Set up button click listeners
        btnCreateGroup.setOnClickListener {
            val groupName = etGroupName.text.toString().trim()
            val totalAmountStr = etTotalAmount.text.toString().trim()
            val memberNames = getMemberNames()

            if (groupName.isEmpty() || totalAmountStr.isEmpty() || memberNames.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Group object and save it
            val newGroup = Group(groupName, memberNames.size, totalAmountStr.toDouble(), memberNames)
            saveGroup(newGroup)

            Toast.makeText(this, "Group '$groupName' created!", Toast.LENGTH_LONG).show()

            // Optional: clear fields after saving
            etGroupName.text.clear()
            etMemberCount.text.clear()
            etTotalAmount.text.clear()
            llMemberNames.removeAllViews()
        }

        btnMyGroups.setOnClickListener {
            val intent = Intent(this, MyGroupsActivity::class.java)
            startActivity(intent)
        }

        // Setup bottom bar listeners
        setupBottomBarListeners()
    }

    private fun updateMemberNameFields(countStr: String) {
        llMemberNames.removeAllViews()
        val count = countStr.toIntOrNull() ?: 0
        currentMemberCount = count

        for (i in 1..count) {
            val textInputLayout = TextInputLayout(this, null, com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox)
            textInputLayout.id = View.generateViewId()
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.bottomMargin = resources.getDimensionPixelSize(R.dimen.material_text_field_bottom_margin)
            textInputLayout.layoutParams = params
            textInputLayout.hint = "Member $i Name"

            val editText = EditText(textInputLayout.context)
            editText.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            editText.id = View.generateViewId()
            editText.inputType = android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS
            editText.tag = "member_name_$i"

            textInputLayout.addView(editText)
            llMemberNames.addView(textInputLayout)
        }
    }

    private fun getMemberNames(): ArrayList<String> {
        val memberNames = ArrayList<String>()
        for (i in 0 until llMemberNames.childCount) {
            val view = llMemberNames.getChildAt(i)
            if (view is TextInputLayout) {
                val editText = view.editText
                val name = editText?.text.toString().trim()
                if (name.isNotEmpty()) {
                    memberNames.add(name)
                }
            }
        }
        return memberNames
    }

    private fun saveGroup(group: Group) {
        val sharedPref = getSharedPreferences("DiviPayGroups", Context.MODE_PRIVATE)
        val gson = Gson()
        val existingGroupsJson = sharedPref.getString("groupsList", null)
        val type = object : TypeToken<ArrayList<Group>>() {}.type
        val groupsList: ArrayList<Group> = if (existingGroupsJson != null) {
            gson.fromJson(existingGroupsJson, type)
        } else {
            ArrayList()
        }
        groupsList.add(group)
        val updatedGroupsJson = gson.toJson(groupsList)
        with(sharedPref.edit()) {
            putString("groupsList", updatedGroupsJson)
            apply()
        }
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