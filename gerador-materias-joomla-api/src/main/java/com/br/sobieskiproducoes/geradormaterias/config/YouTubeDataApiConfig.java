/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;

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

  private Credential authorize() throws Exception {
    final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            properties.getYoutube().getClientId(),
            properties.getYoutube().getSecrets(),
            Collections.singletonList(YouTubeScopes.YOUTUBE_READONLY))
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
        .setAccessType("offline")
        .build();

    final LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
}

// @Bean
  public YouTube getYouTubeService() throws Exception {
    log.info("Autenticação no youtube por Credenciais");
    return new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(),
        authorize()).setApplicationName(properties.getYoutube().getAppId()).build();
  }

  @Bean
  public YouTube getYouTubeServiceSimples() throws IOException {
    log.info("Autenticação no simples");
    return new YouTube.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
        GsonFactory.getDefaultInstance(), request -> {
        }).setApplicationName(properties.getYoutube().getAppId()).build();
  }
}
