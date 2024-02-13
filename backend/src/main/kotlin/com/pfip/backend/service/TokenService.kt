package com.pfip.backend.service

import com.pfip.backend.dto.AccountType
import com.pfip.backend.dto.TokenData
import com.pfip.backend.model.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
public class TokenService(
    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder
) {
    fun createToken(user: Account): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(30L, ChronoUnit.DAYS))
            .subject(user.username)
            .claim("username", user.username)
            .claim("type",user.type)
            .claim("typeId",user.id)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }


    fun parseToken(token: String): TokenData? {
        return try {
            val jwt = jwtDecoder.decode(token)
            val username = jwt.claims["username"] as String
            val type= jwt.claims["type"] as String
            val ret=AccountType.valueOf(type)
            val typeId=jwt.claims["typeId"] as Long
            TokenData(username,ret,typeId.toInt())
        } catch (e: Exception) {
            null
        }
    }
}