package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.adapter.StudentAdapter
import com.example.studentapp.model.Student
import com.example.studentapp.model.StudentRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // Set activity title
        supportActionBar?.title = "Students List"
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Initialize adapter
        adapter = StudentAdapter(
            StudentRepository.getAllStudents(),
            { student -> openStudentDetails(student) },
            { studentId -> toggleStudentChecked(studentId) }
        )
        recyclerView.adapter = adapter
        
        // Set up the add button
        val addButton = findViewById<FloatingActionButton>(R.id.add_student_button)
        addButton.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the adapter with the latest data when returning to this activity
        adapter.updateStudents(StudentRepository.getAllStudents())
    }
    
    private fun openStudentDetails(student: Student) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("STUDENT_ID", student.id)
        startActivity(intent)
    }
    
    private fun toggleStudentChecked(studentId: String) {
        StudentRepository.toggleStudentChecked(studentId)
        adapter.updateStudents(StudentRepository.getAllStudents())
    }
}