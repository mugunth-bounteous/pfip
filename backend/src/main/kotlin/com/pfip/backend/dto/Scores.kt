package com.pfip.backend.dto

import jakarta.persistence.Entity
import jakarta.persistence.Id


data class Scores(
    val studentId: Int,
    val score: Double
)
