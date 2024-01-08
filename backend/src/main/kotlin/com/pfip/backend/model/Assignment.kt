package com.pfip.backend.model

import com.pfip.backend.dto.Scores
import jakarta.persistence.*

@Entity
class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null;
    var assignmentName: String? = null;
    var courseId: Int? = null;
    @ElementCollection
    var studentScores: MutableMap<Int, Double>? = mutableMapOf()

    fun AddStudentScore(index: Int, value: Double) {
        studentScores?.put(index, value)
    }

    fun GetStudentScore(studentId: Int): Double {
        return studentScores?.getOrDefault(studentId, Double.NaN) ?: Double.NaN
    }
}