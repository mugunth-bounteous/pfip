package com.pfip.backend.dto

data class TokenData(
    val username: String,
    val type: AccountType,
    val typeId: Int
)
