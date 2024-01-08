package com.pfip.backend.dto

import com.pfip.backend.model.Course
import com.pfip.backend.model.Student

data class StudentDetails(
    val courses: List<Course>,
    val student: Student
)
