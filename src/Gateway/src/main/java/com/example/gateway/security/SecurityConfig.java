//package com.example.gateway.security;
//
//import com.example.IdentityProvider.security.TokenAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//@ComponentScan(basePackages = "com.example.IdentityProvider.repository")
//@ComponentScan(basePackages = "com.example.IdentityProvider.security")
//public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private final TokenAuthenticationFilter tokenAuthenticationFilter;
//
//
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers("/public-endpoint").permitAll() // Доступ для всех
//                .anyRequest().authenticated() // Остальные запросы требуют аутентификации
//                .and()
//                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//}
