package com.pfip.backend.config

import com.pfip.backend.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val tokenService: TokenService,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // Define public and private routes
        http.authorizeHttpRequests{auth -> auth
            .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
            .requestMatchers("/api/assignment/**").authenticated()
            .requestMatchers("/api/course/**").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/course/create-course").permitAll()
            .requestMatchers("/api/test/**").authenticated()
            .requestMatchers("/api/parent/**").authenticated()
            .anyRequest().permitAll()
        }




        // Configure JWT
        http.oauth2ResourceServer {  auth->auth.jwt{} }
        http.authenticationManager { auth ->
            val jwt = auth as BearerTokenAuthenticationToken
            val user = tokenService.parseToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")
            UsernamePasswordAuthenticationToken(user, "", listOf(SimpleGrantedAuthority("USER")))
        }

//        // Other configuration
        http.cors {  }
        http.sessionManagement { v->v.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        http.csrf { v->v.disable() }
        http.headers { v->v.frameOptions { k->k.disable() } }
        http.headers { v->v.xssProtection { k->k.disable() } }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        // allow localhost for dev purposes
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}