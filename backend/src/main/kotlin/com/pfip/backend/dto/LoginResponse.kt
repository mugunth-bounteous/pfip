package com.pfip.backend.dto

public data class LoginResponse(
    val token: String,
    val username: String,
    val typeId: Int,
    val type: AccountType
)
