package com.example.studentapp.model

// Singleton repository class to store students in memory
object StudentRepository {
    private val students = mutableListOf<Student>()

    init {
        // Add some sample data
        addStudent(Student("1", "John Doe", false, "123-456-7890", "123 Main St", "john@example.com"))
        addStudent(Student("2", "Jane Smith", true, "098-765-4321", "456 Elm St", "jane@example.com"))
    }

    fun getAllStudents(): List<Student> {
        return students.toList()
    }

    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(student: Student) {
        val index = students.indexOfFirst { it.id == student.id }
        if (index != -1) {
            students[index] = student
        }
    }

    fun updateStudentWithNewId(oldId: String, student: Student) {
        val index = students.indexOfFirst { it.id == oldId }
        if (index != -1) {
            students[index] = student
        }
    }

    fun deleteStudent(id: String) {
        students.removeIf { it.id == id }
    }

    fun toggleStudentChecked(id: String) {
        val student = getStudentById(id)
        student?.let {
            it.checked = !it.checked
        }
    }
} 