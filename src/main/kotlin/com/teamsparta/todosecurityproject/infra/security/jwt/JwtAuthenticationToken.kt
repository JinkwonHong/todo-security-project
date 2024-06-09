package com.teamsparta.todosecurityproject.infra.security.jwt

import com.teamsparta.todosecurityproject.infra.security.UserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import java.io.Serializable

class JwtAuthenticationToken(
    private val principal: UserPrincipal,
    details: Any
) : AbstractAuthenticationToken(emptyList()), Serializable {

    init {
        super.setAuthenticated(true)
        super.setDetails(details)
    }
    override fun getCredentials() = null

    override fun getPrincipal() = principal

    override fun isAuthenticated(): Boolean {
        return true
    }
}
