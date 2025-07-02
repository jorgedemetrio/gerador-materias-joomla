package com.br.sobieskiproducoes.geradormaterias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.br.sobieskiproducoes.geradormaterias.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties({ ChatGPTProperties.class, ConfiguracoesProperties.class })
@EnableWebMvc
@EnableEncryptableProperties
@EnableWebSecurity
@EnableJpaAuditing
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
