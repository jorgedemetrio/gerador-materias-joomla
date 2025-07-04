package com.br.sobieskiproducoes.geradormaterias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecutiryConfig {

    private final SecurityFilterComponent securityFilterComponent;

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(final HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(CsrfConfigurer::disable).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        /**
                         * Login
                         */
                        .requestMatchers("/api/v1/autenticacao", "/api/v1/autenticacao/", "/api/v1/autenticacao/*", "/api/v1/autenticacao/login").permitAll()
                        .requestMatchers("/login", "/login/*").permitAll()
                        .requestMatchers("/v3/api-docs", "/v3/api-docs/*", "/swagger-ui", "/swagger-ui/*", "/swagger-ui.html").permitAll()
                        .requestMatchers("/actuator", "/actuator/*").permitAll().requestMatchers("/", "/home", "/register").permitAll()

                        /**
                         * Usuário
                         */
                        .requestMatchers(HttpMethod.POST, "/usuario").hasRole("CRIAR_USUARIO").requestMatchers(HttpMethod.PUT, "/usuario")
                        .hasRole("ALTERAR_USUARIO").requestMatchers(HttpMethod.DELETE, "/usuario/*").hasRole("REMOVER_USUARIO")
                        .requestMatchers(HttpMethod.GET, "/usuario").hasRole("VER_USUARIO").requestMatchers(HttpMethod.POST, "/api/v1/usuario")
                        .hasRole("CRIAR_USUARIO").requestMatchers(HttpMethod.PUT, "/api/v1/usuario").hasRole("ALTERAR_USUARIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/usuario/*").hasRole("REMOVER_USUARIO").requestMatchers(HttpMethod.GET, "/api/v1/usuario")
                        .hasRole("VER_USUARIO").requestMatchers("/api/v1/usuario", "/usuario").permitAll()

                        /**
                         * Matéria
                         */
                        .requestMatchers(HttpMethod.POST, "/materia").hasRole("CRIAR_MATERIA").requestMatchers(HttpMethod.PUT, "/materia")
                        .hasRole("ALTERAR_MATERIA").requestMatchers(HttpMethod.DELETE, "/materia/*").hasRole("REMOVER_MATERIA")
                        .requestMatchers(HttpMethod.GET, "/materia").hasRole("VER_MATERIA").requestMatchers(HttpMethod.GET, "/materia/publicar/*")
                        .hasRole("PUBLICAR_MATERIA").requestMatchers(HttpMethod.GET, "/lote/materia").hasRole("CRIAR_MATERIA_EM_LOTE")
                        .requestMatchers(HttpMethod.GET, "/lote/titulo").hasRole("CRIAR_SUGESTAO_TITULO_EM_LOTE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/materia").hasRole("CRIAR_MATERIA").requestMatchers(HttpMethod.PUT, "/api/v1/materia")
                        .hasRole("ALTERAR_MATERIA").requestMatchers(HttpMethod.DELETE, "/api/v1/materia/*").hasRole("REMOVER_MATERIA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/materia").hasRole("VER_MATERIA").requestMatchers(HttpMethod.GET, "/api/v1/materia/publicar/*")
                        .hasRole("PUBLICAR_MATERIA").requestMatchers(HttpMethod.GET, "/api/v1/lote/materia").hasRole("CRIAR_MATERIA_EM_LOTE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/lote/titulo").hasRole("CRIAR_SUGESTAO_TITULO_EM_LOTE")

                        /**
                         * TAG
                         */
                        .requestMatchers(HttpMethod.POST, "/tag").hasRole("CRIAR_TAG").requestMatchers(HttpMethod.PUT, "/tag").hasRole("ALTERAR_TAG")
                        .requestMatchers(HttpMethod.DELETE, "/tag/*").hasRole("REMOVER_TAG").requestMatchers(HttpMethod.GET, "/tag").hasRole("VER_TAG")
                        .requestMatchers(HttpMethod.POST, "/api/v1/tag").hasRole("CRIAR_TAG").requestMatchers(HttpMethod.PUT, "/api/v1/tag")
                        .hasRole("ALTERAR_TAG").requestMatchers(HttpMethod.DELETE, "/api/v1/tag/*").hasRole("REMOVER_TAG")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tag").hasRole("VER_TAG")

                        /**
                         * Termos da empresa
                         */

                        .requestMatchers(HttpMethod.POST, "/termo-empresa").hasRole("CRIAR_TERMO_EMPRESA").requestMatchers(HttpMethod.PUT, "/termo-empresa")
                        .hasRole("ALTERAR_TERMO_EMPRESA").requestMatchers(HttpMethod.DELETE, "/termo-empresa/*").hasRole("REMOVER_TERMO_EMPRESA")
                        .requestMatchers(HttpMethod.GET, "/termo-empresa").hasRole("VER_TERMO_EMPRESA")
                        .requestMatchers(HttpMethod.POST, "/api/v1/termo-empresa").hasRole("CRIAR_TERMO_EMPRESA")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/termo-empresa").hasRole("ALTERAR_TERMO_EMPRESA")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/termo-empresa/*").hasRole("REMOVER_TERMO_EMPRESA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/termo-empresa").hasRole("VER_TERMO_EMPRESA")

                        /**
                         * Audiencia da empresa
                         */

                        .requestMatchers(HttpMethod.POST, "/audiencia-empresa").hasRole("CRIAR_AUDIENCIA_EMPRESA")
                        .requestMatchers(HttpMethod.PUT, "/audiencia-empresa").hasRole("ALTERAR_AUDIENCIA_EMPRESA")
                        .requestMatchers(HttpMethod.DELETE, "/audiencia-empresa/*").hasRole("REMOVER_AUDIENCIA_EMPRESA")
                        .requestMatchers(HttpMethod.GET, "/audiencia-empresa").hasRole("VER_AUDIENCIA_EMPRESA")
                        .requestMatchers(HttpMethod.POST, "/api/v1/audiencia-empresa").hasRole("CRIAR_AUDIENCIA_EMPRESA")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/audiencia-empresa").hasRole("ALTERAR_AUDIENCIA_EMPRESA")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/audiencia-empresa/*").hasRole("REMOVER_AUDIENCIA_EMPRESA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/audiencia-empresa").hasRole("VER_AUDIENCIA_EMPRESA")

                        /**
                         * Categoria
                         */
                        .requestMatchers(HttpMethod.POST, "/categoria").hasRole("CRIAR_CATEGORIA").requestMatchers(HttpMethod.PUT, "/categoria")
                        .hasRole("ALTERAR_CATEGORIA").requestMatchers(HttpMethod.DELETE, "/categoria/*").hasRole("REMOVER_CATEGORIA")
                        .requestMatchers(HttpMethod.GET, "/categoria").hasRole("VER_CATEGORIA").requestMatchers(HttpMethod.POST, "/api/v1/categoria")
                        .hasRole("CRIAR_CATEGORIA").requestMatchers(HttpMethod.PUT, "/api/v1/categoria").hasRole("ALTERAR_CATEGORIA")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categoria/*").hasRole("REMOVER_CATEGORIA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/categoria").hasRole("VER_CATEGORIA")

                        /**
                         * site
                         */
                        .requestMatchers(HttpMethod.POST, "/site").hasRole("CRIAR_SITE").requestMatchers(HttpMethod.PUT, "/site").hasRole("ALTERAR_SITE")
                        .requestMatchers(HttpMethod.DELETE, "/site/*").hasRole("REMOVER_SITE").requestMatchers(HttpMethod.GET, "/site").hasRole("VER_SITE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/site").hasRole("CRIAR_SITE").requestMatchers(HttpMethod.PUT, "/api/v1/site")
                        .hasRole("ALTERAR_SITE").requestMatchers(HttpMethod.DELETE, "/api/v1/site/*").hasRole("REMOVER_SITE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/site").hasRole("VER_SITE")

                        /**
                         * Keywords
                         */
                        .requestMatchers(HttpMethod.POST, "/site/*/palavrachave").hasRole("CRIAR_PALAVRACHAVE")
                        .requestMatchers(HttpMethod.PUT, "/site/*/palavrachave").hasRole("ALTERAR_PALAVRACHAVE")
                        .requestMatchers(HttpMethod.DELETE, "/site/*/palavrachave/*").hasRole("REMOVER_PALAVRACHAVE")
                        .requestMatchers(HttpMethod.GET, "/site/*/palavrachave").hasRole("VER_PALAVRACHAVE")

                        .requestMatchers(HttpMethod.GET, "/lote/palavrachave").hasRole("CARREGAR_PALAVRASCHAVES_LOTE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/site/*/palavrachave").hasRole("CRIAR_PALAVRACHAVE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/site/*/palavrachave").hasRole("ALTERAR_PALAVRACHAVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/site/*/palavrachave/*").hasRole("REMOVER_PALAVRACHAVE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/site/*/palavrachave").hasRole("VER_PALAVRACHAVE")

                        .requestMatchers(HttpMethod.GET, "/api/v1/lote/palavrachave").hasRole("CARREGAR_PALAVRASCHAVES_LOTE")

                        /**
                         * Empresa
                         */
                        .requestMatchers(HttpMethod.GET, "/empresa").hasRole("ALTERAR_EMPRESA").requestMatchers(HttpMethod.GET, "/api/v1/empresa")
                        .hasRole("ALTERAR_EMPRESA")

                        .anyRequest().authenticated()

                )
//                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/dashboard").permitAll())
                .formLogin(form -> form.loginPage("/login").usernameParameter("username").failureUrl("/login?loginError=true"))
                .logout(logout -> logout.logoutSuccessUrl("/login?logoutSuccess=true").deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")))
                .logout(LogoutConfigurer::permitAll).addFilterBefore(securityFilterComponent, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}
