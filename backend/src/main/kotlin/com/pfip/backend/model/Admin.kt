package com.pfip.backend.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null
    val adminName: String? = null
    val username: String?=null
}