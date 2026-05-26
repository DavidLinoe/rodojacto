package com.example.demo.security

import com.example.demo.repository.CollaboratorRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val collaboratorRepository: CollaboratorRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7)
            if (jwtService.isValid(token) && SecurityContextHolder.getContext().authentication == null) {
                val email = jwtService.extractEmail(token)
                val collaborator = collaboratorRepository.findByEmail(email)
                if (collaborator != null) {
                    val auth = UsernamePasswordAuthenticationToken(
                        collaborator,
                        null,
                        listOf(SimpleGrantedAuthority("ROLE_${collaborator.accessLevel.name}"))
                    )
                    auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = auth
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}
