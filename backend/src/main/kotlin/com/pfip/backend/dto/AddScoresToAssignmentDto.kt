package com.pfip.backend.dto

data class AddScoresToAssignmentDto(
    val assignmentId: Int,
    val ScoresList: ArrayList<Scores>,
    val courseId: Int
)
