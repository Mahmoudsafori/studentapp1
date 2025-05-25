package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.model.StudentRepository

class StudentDetailsActivity : AppCompatActivity() {
    private var studentId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Student Details"

        // Get student ID from intent
        studentId = intent.getStringExtra("STUDENT_ID") ?: ""
        if (studentId.isEmpty()) {
            finish()
            return
        }

        // Find the student in repository
        val student = StudentRepository.getStudentById(studentId)
        if (student == null) {
            finish()
            return
        }

        // Get references to views
        val idTextView = findViewById<TextView>(R.id.id_value)
        val nameTextView = findViewById<TextView>(R.id.name_value)
        val phoneTextView = findViewById<TextView>(R.id.phone_value)
        val addressTextView = findViewById<TextView>(R.id.address_value)
        val emailTextView = findViewById<TextView>(R.id.email_value)
        val editButton = findViewById<Button>(R.id.edit_button)
        val backButton = findViewById<Button>(R.id.back_button)

        // Set back button click listener
        backButton.setOnClickListener {
            finish()
        }

        // Set student details
        idTextView.text = student.id
        nameTextView.text = student.name
        phoneTextView.text = student.phone
        addressTextView.text = student.address
        emailTextView.text = student.email

        // Set edit button click listener
        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("STUDENT_ID", studentId)
            startActivity(intent)
        }
    }

    // Refresh data when returning to this activity
    override fun onResume() {
        super.onResume()
        
        // Get the student again in case it was updated
        val student = StudentRepository.getStudentById(studentId)
        
        // If the student was deleted, close this activity
        if (student == null) {
            finish()
            return
        }
        
        // Update the UI with current data
        val idTextView = findViewById<TextView>(R.id.id_value)
        val nameTextView = findViewById<TextView>(R.id.name_value)
        val phoneTextView = findViewById<TextView>(R.id.phone_value)
        val addressTextView = findViewById<TextView>(R.id.address_value)
        val emailTextView = findViewById<TextView>(R.id.email_value)
        
        idTextView.text = student.id
        nameTextView.text = student.name
        phoneTextView.text = student.phone
        addressTextView.text = student.address
        emailTextView.text = student.email
    }
    
    // Handle back button click in action bar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 