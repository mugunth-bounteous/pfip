package com.pfip.backend.dto

data class AddScoresToTestDto(
    val testId: Int,
    val ScoresList: ArrayList<Scores>,
    val courseId: Int
)
