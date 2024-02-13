package com.pfip.backend.dto

public data class TokenData(
    val username: String,
    val type: AccountType,
    val typeId: Int
)
