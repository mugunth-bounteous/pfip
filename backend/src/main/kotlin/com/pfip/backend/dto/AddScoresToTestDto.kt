package com.pfip.backend.dto

public data class AddScoresToTestDto(
    val testId: Int,
    val ScoresList: ArrayList<Scores>,
    val courseId: Int
)
