package com.br.sobieskiproducoes.geradormateriasjoomla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTConfigurationProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.JoomlaConfigurationProperties;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
@EnableCaching
@EnableDiscoveryClient
@EnableConfigurationProperties({ JoomlaConfigurationProperties.class, ChatGPTConfigurationProperties.class,
    ChatGPTProperties.class })
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
