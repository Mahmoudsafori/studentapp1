package com.example.studentapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.model.Student
import com.example.studentapp.model.StudentRepository
import com.google.android.material.textfield.TextInputEditText

class NewStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)
        
        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add New Student"

        // Get references to views
        val idEditText = findViewById<TextInputEditText>(R.id.id_edit_text)
        val nameEditText = findViewById<TextInputEditText>(R.id.name_edit_text)
        val phoneEditText = findViewById<TextInputEditText>(R.id.phone_edit_text)
        val addressEditText = findViewById<TextInputEditText>(R.id.address_edit_text)
        val emailEditText = findViewById<TextInputEditText>(R.id.email_edit_text)
        val saveButton = findViewById<Button>(R.id.save_button)
        val backButton = findViewById<Button>(R.id.back_button)

        // Set back button click listener
        backButton.setOnClickListener {
            finish()
        }

        // Set up save button click listener
        saveButton.setOnClickListener {
            val id = idEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()

            // Validate input
            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "ID and Name are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if ID already exists
            if (StudentRepository.getStudentById(id) != null) {
                Toast.makeText(this, "A student with this ID already exists", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create and add new student
            val newStudent = Student(
                id = id,
                name = name,
                phone = phone,
                address = address,
                email = email
            )
            
            StudentRepository.addStudent(newStudent)
            
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
            finish() // Return to previous activity
        }
    }

    // Handle back button click in action bar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 