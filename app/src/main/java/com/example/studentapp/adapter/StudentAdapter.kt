package com.example.studentapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp.R
import com.example.studentapp.model.Student

class StudentAdapter(
    private var students: List<Student>,
    private val onItemClick: (Student) -> Unit,
    private val onCheckedChange: (String) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.student_image)
        val nameTextView: TextView = view.findViewById(R.id.student_name)
        val idTextView: TextView = view.findViewById(R.id.student_id)
        val checkBox: CheckBox = view.findViewById(R.id.student_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        
        holder.nameTextView.text = student.name
        holder.idTextView.text = "ID: ${student.id}"
        holder.checkBox.isChecked = student.checked
        
        // Set the default student image
        holder.imageView.setImageResource(R.drawable.default_student_image)
        
        // Set click listeners
        holder.itemView.setOnClickListener {
            onItemClick(student)
        }
        
        holder.checkBox.setOnClickListener {
            onCheckedChange(student.id)
        }
    }

    override fun getItemCount() = students.size

    // Method to update the data set
    fun updateStudents(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }
} 