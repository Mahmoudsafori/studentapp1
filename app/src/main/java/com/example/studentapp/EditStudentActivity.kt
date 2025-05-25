package com.example.studentapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.model.Student
import com.example.studentapp.model.StudentRepository
import com.google.android.material.textfield.TextInputEditText

class EditStudentActivity : AppCompatActivity() {
    private var studentId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Student"

        // Get student ID from intent
        studentId = intent.getStringExtra("STUDENT_ID") ?: ""
        if (studentId.isEmpty()) {
            finish()
            return
        }

        // Find the student in repository
        val student = StudentRepository.getStudentById(studentId) ?: run {
            finish()
            return
        }

        // Get references to views
        val idEditText = findViewById<TextInputEditText>(R.id.id_edit_text)
        val nameEditText = findViewById<TextInputEditText>(R.id.name_edit_text)
        val phoneEditText = findViewById<TextInputEditText>(R.id.phone_edit_text)
        val addressEditText = findViewById<TextInputEditText>(R.id.address_edit_text)
        val emailEditText = findViewById<TextInputEditText>(R.id.email_edit_text)
        val updateButton = findViewById<Button>(R.id.update_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)
        val backButton = findViewById<Button>(R.id.back_button)

        // Set back button click listener
        backButton.setOnClickListener {
            finish()
        }

        // Set current student details to form
        idEditText.setText(student.id)
        nameEditText.setText(student.name)
        phoneEditText.setText(student.phone)
        addressEditText.setText(student.address)
        emailEditText.setText(student.email)

        // Set update button click listener
        updateButton.setOnClickListener {
            val newId = idEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()

            // Validate input
            if (newId.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "ID and Name are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if new ID exists and it's not the current student's ID
            if (newId != studentId && StudentRepository.getStudentById(newId) != null) {
                Toast.makeText(this, "A student with this ID already exists", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create updated student
            val updatedStudent = Student(
                id = newId,
                name = name,
                checked = student.checked,
                phone = phone,
                address = address,
                email = email
            )

            // Update student in repository
            if (newId == studentId) {
                StudentRepository.updateStudent(updatedStudent)
            } else {
                StudentRepository.updateStudentWithNewId(studentId, updatedStudent)
            }

            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Set delete button click listener
        deleteButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Delete") { _, _ ->
                StudentRepository.deleteStudent(studentId)
                Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    // Handle back button click in action bar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 