package com.pfip.backend.dto

data class CreateUserDto(
    val username: String,
    val password: String,
    val type: AccountType
)
