/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.sobieskiproducoes.geradormaterias.usuario.service.UsuarioDetailsService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 01:54:50
 */
@Configuration
@EnableConfigurationProperties
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UsuarioDetailsService userDetailsService;

  protected void configure(final AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }

  protected void configure(final HttpSecurity http) throws Exception {
    http.httpBasic().and().authorizeRequests().antMatchers("/h2-console/**").permitAll().antMatchers("/").permitAll().antMatchers("/books").hasRole("USER")
        .antMatchers("/books2").hasRole("ADMIN").and().csrf().disable().headers().frameOptions().disable();
  }
}
