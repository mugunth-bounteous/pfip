package com.pfip.backend.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
public class PasswordService(private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    fun encodePassword(rawPassword: String): String {
        return bCryptPasswordEncoder.encode(rawPassword)
    }

    fun checkPassword(rawPassword: String, encodedPassword: String): Boolean {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword)
    }
}