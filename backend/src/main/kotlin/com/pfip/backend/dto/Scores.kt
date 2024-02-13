package com.pfip.backend.dto

import jakarta.persistence.Entity
import jakarta.persistence.Id


public data class Scores(
    val studentId: Int,
    val score: Double
)
