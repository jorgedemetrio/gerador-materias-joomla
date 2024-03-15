/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 13 de mar. de 2024 21:00:44
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Configuration
public class YouTubeDataApiConfig {

  private final ConfiguracoesProperties properties;

  @Bean
  public YouTube getYouTubeService() throws IOException {
//    final Credential credential = new GoogleCredential()
//        .setAccessToken(properties.getYoutube().getChaveApi());
//    return new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory(), credential)
//        .setApplicationName(properties.getYoutube().getAppId()).build();

    // YouTube youtubeService = new YouTube.Builder(new
    // com.google.api.client.http.javanet.NetHttpTransport(), new
    // com.google.api.client.json.jackson2.JacksonFactory(), request ->
    // {}).setApplicationName(APPLICATION_NAME).build();

    return new YouTube.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
        GsonFactory.getDefaultInstance(), request -> {
        }).setApplicationName(properties.getYoutube().getAppId()).build();

  }
}
