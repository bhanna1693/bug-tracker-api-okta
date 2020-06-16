package com.bhanna.bugtracker

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.RequestMatcher

@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        //@formatter:off
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .oauth2Login()
                .and()
                .oauth2ResourceServer().jwt()

        //Force HTTPS in production
        http.requiresChannel()
                .requestMatchers(RequestMatcher { r ->
                    r.getHeader("X-Forwarded-Proto") != null
                }).requiresSecure()

        //Configure the CSRF Cookie so it can be read by JavaScript
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        //Configure a CSP that only allows local scripts
        http.headers()
                .contentSecurityPolicy("script-src 'self'; report-to /csp-report-endpoint/")
        //@formatter:on
    }
}