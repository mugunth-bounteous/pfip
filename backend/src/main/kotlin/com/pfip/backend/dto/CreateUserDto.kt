package com.pfip.backend.dto

public data class CreateUserDto(
    val username: String,
    val password: String,
    val type: AccountType
)
