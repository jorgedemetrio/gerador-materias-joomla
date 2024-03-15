/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.consumer;

import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeVideoDTO;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Caption;
import com.google.api.services.youtube.model.CaptionListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 13 de mar. de 2024 21:06:23
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Component
public class YoutubePlaylistParser {

  private static final String PART = "snippet,contentDetails,statistics,status";
  private final YouTube youtubeService;
  private final ConfiguracoesProperties properties;

  private String extractListParam(final String playlistUrl) throws Exception {
    final HttpClient client = HttpClient.newHttpClient();
    final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(playlistUrl)).GET().build();

    final HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    final Map<String, List<String>> params = new HashMap<>();
    List<String> paramList = new ArrayList<>();
    for (final String param : response.body().split("&")) {
      final String[] keyValue = param.split("=");
      paramList = new ArrayList<>();
      paramList.add(URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name()));
      params.put(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name()), paramList);
    }

    final List<String> listValues = params.getOrDefault("list", Collections.emptyList());
    return listValues.isEmpty() ? null : listValues.get(0);

  }

  private String extractVideoParam(final String playlistUrl) throws Exception {
    final HttpClient client = HttpClient.newHttpClient();
    final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(playlistUrl)).GET().build();

    final HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    final Map<String, List<String>> params = new HashMap<>();
    List<String> paramList = new ArrayList<>();
    for (final String param : response.body().split("&")) {
      final String[] keyValue = param.split("=");
      paramList = new ArrayList<>();
      paramList.add(URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name()));
      params.put(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name()), paramList);
    }

    final List<String> listValues = params.getOrDefault("v", Collections.emptyList());
    return listValues.isEmpty() ? null : listValues.get(0);

  }

  public YoutubeVideoDTO getDadosVideo(final String url) throws Exception {
    // JacksonFactory

    final String videoId = extractVideoParam(url);

    final YouTube youtube = youtubeService;

    final YouTube.Videos.List request = youtube.videos().list(PART);
    request.setId(videoId);

    final VideoListResponse response = request.execute();

    final List<Video> itens = response.getItems();


    return itens.size() > 0 ? getYoutubeVideoDTO(itens.get(0)) : null;

  }

  public List<YoutubeVideoDTO> getDadosVideos(final String playlistUrl) throws Exception {
    final String idListParam = extractListParam(playlistUrl);
    final List<Video> videos = getVideosFromPlaylist(idListParam);
    final List<YoutubeVideoDTO> titlesAndDescriptions = new ArrayList<>();

    for (final Video video : videos) {
      final String title = video.getSnippet().getTitle();
      final String description = video.getSnippet().getDescription();
      titlesAndDescriptions.add(getYoutubeVideoDTO(video));

    }

    return titlesAndDescriptions;
  }

  public String getLegenda(final String videoId) throws Exception {
    // JacksonFactory
    final YouTube youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), new GsonFactory(),
        request -> {
        }).setApplicationName(properties.getYoutube().getAppId()).build();

    final YouTube.Captions.List request = youtube.captions().list(PART, videoId);
    // request.setKey(properties.getYoutube().getChaveApi());
    // request.setPart(PART);

    final CaptionListResponse response = request.execute();
    final List<Caption> captionsList = response.getItems();
    if (captionsList != null && !captionsList.isEmpty()) {
      final Caption captions = captionsList.get(0);
      return captions.getSnippet().getTrackKind() + ": " + captions.getSnippet().keySet().stream()
          .map(n -> captions.getSnippet().get(n).toString()).collect(Collectors.joining());
    }

    return "Nenhuma legenda encontrada.";
  }

  private List<Video> getVideosFromPlaylist(final String playlistId) throws Exception {
    final YouTube youtube = youtubeService;

    final YouTube.PlaylistItems.List request = youtube.playlistItems().list("id,snippet");
    request.setKey(properties.getYoutube().getChaveApi());
    request.setPlaylistId(playlistId);
    request.setMaxResults((long) 50);
    request.setPart("snippet");

    final PlaylistItemListResponse response = request.execute();
    final List<Video> videos = new ArrayList<>();

//    for (final PlaylistItemSnippet playlistItemSnippet : response.getItems()) {
//      final String videoId = playlistItemSnippet.getResourceId().getVideoId();

    final List<PlaylistItem> itens = response.getItems();

    for (final PlaylistItem playlistItemSnippet : itens) {
      final String videoId = playlistItemSnippet.getId();

      final YouTube.Videos.List videoRequest = youtube.videos().list(videoId);
      videoRequest.setKey(properties.getYoutube().getChaveApi());
      videoRequest.setId(videoId);
      videoRequest.setPart("snippet");

      final VideoListResponse videoResponse = videoRequest.execute();
      videos.add(videoResponse.getItems().get(0));
    }

    return videos;
  }

  private YoutubeVideoDTO getYoutubeVideoDTO(final Video video) throws Exception {
    final String title = video.getSnippet().getTitle();
    final String description = video.getSnippet().getDescription();
    return new YoutubeVideoDTO(title, description, getLegenda(video.getId()), video.getSnippet().getTags());
  }
}
