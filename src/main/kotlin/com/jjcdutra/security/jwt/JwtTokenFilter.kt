package com.jjcdutra.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtTokenFilter(
    @field:Autowired private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val token = jwtTokenProvider.resolveToken(req as HttpServletRequest)

        if (!token.isNullOrBlank() && jwtTokenProvider.validateToken(token)) {
            val auth = jwtTokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(req, res)
    }
}