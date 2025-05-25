package com.example.studentapp.model

data class Student(
    var id: String,
    var name: String,
    var checked: Boolean = false,
    // Additional student details can be added here
    var phone: String = "",
    var address: String = "",
    var email: String = ""
) 