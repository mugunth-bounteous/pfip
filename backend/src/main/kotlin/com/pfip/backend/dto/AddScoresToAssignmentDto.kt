package com.pfip.backend.dto

public data class AddScoresToAssignmentDto(
    val assignmentId: Int,
    val ScoresList: ArrayList<Scores>,
    val courseId: Int
)
