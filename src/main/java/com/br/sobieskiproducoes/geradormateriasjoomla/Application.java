package com.br.sobieskiproducoes.geradormateriasjoomla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTConfigurationProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.JoomlaConfigurationProperties;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
@EnableConfigurationProperties({ JoomlaConfigurationProperties.class, ChatGPTConfigurationProperties.class,
    ChatGPTProperties.class })
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
