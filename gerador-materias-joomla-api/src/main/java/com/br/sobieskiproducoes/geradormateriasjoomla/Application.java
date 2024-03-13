package com.br.sobieskiproducoes.geradormateriasjoomla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.annotations.OpenAPI31;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
@EnableCaching
@EnableDiscoveryClient
@OpenAPI31
@EnableScheduling
@OpenAPIDefinition(info = @Info(description = "Gerador de matérias e adminitração do Joomla", version = "0.0.1-SNAPSHOT", title = "Gerador de Conteúdo com AI", termsOfService = "https://www.sobieskiproducoes.com.br/termos", summary = "/", contact = @Contact(name = "Jorge Demetrio", email = "jorge@sobieskiproducoes.com.br", url = "https://www.sobieskiproducoes.com.br")))
@EnableConfigurationProperties({ ChatGPTProperties.class, ConfiguracoesProperties.class })
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
