/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.consumer;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.springframework.stereotype.Component;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeVideoDTO;
import com.google.api.services.youtube.YouTube;
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
    final String params = playlistUrl.substring(playlistUrl.indexOf("?") + 1);
    final String[] paramsArray = params.split("&");
    String[] keyValue = null;
    for (final String param : paramsArray) {
      keyValue = param.split("=");
      if ("list".equals(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name()))) {
        return URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
      }
    }
    return null;
  }

  private String extractVideoParam(final String playlistUrl) throws Exception {
    final String params = playlistUrl.substring(playlistUrl.indexOf("?") + 1);
    final String[] paramsArray = params.split("&");
    String[] keyValue = null;
    for (final String param : paramsArray) {
      keyValue = param.split("=");
      if ("v".equals(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name()))) {
        return URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
      }
    }
    return null;

  }

  public YoutubeVideoDTO getDadosVideo(final String url) throws Exception {
    // JacksonFactory

    final String videoId = extractVideoParam(url);

    final YouTube.Videos.List request = youtubeService.videos().list(PART);
    request.setId(videoId);
    request.setKey(properties.getYoutube().getChaveApi());

    final VideoListResponse response = request.execute();

    final List<Video> itens = response.getItems();

    return itens.size() > 0 ? getYoutubeVideoDTO(itens.get(0)) : null;

  }

  public List<YoutubeVideoDTO> getDadosVideos(final String playlistUrl) throws Exception {
    final String idListParam = extractListParam(playlistUrl);
    final List<Video> videos = getVideosFromPlaylist(idListParam);
    final List<YoutubeVideoDTO> titlesAndDescriptions = new ArrayList<>();

    for (final Video video : videos) {
      titlesAndDescriptions.add(getYoutubeVideoDTO(video));
    }

    return titlesAndDescriptions;
  }

  public String getLegenda(final String videoId) throws Exception {
    // JacksonFactory
//    final YouTube youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), new GsonFactory(),
//        request -> {
//        }).setApplicationName(properties.getYoutube().getAppId()).build();

//     ByteArrayOutputStream resultStream;
//
//    YouTube.Captions.Download request1;

    final YouTube.Captions.List request2 = youtubeService.captions().list("id, snippet", videoId);
    request2.setKey(properties.getYoutube().getChaveApi());
    request2.setVideoId(videoId);
    // request.setPart(PART);

    final CaptionListResponse response = request2.execute();
    final StringBuilder captionsInfo = new StringBuilder();

    response.getItems().forEach(caption -> {
      final ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
      try {
        final YouTube.Captions.Download request1 = youtubeService.captions().download(caption.getId());
        request1.setKey(properties.getYoutube().getChaveApi());
        request1.getMediaHttpDownloader();
        request1.executeMediaAndDownloadTo(resultStream);
      } catch (final Exception ex) {
        log.log(Level.SEVERE, ex.getMessage(), ex.getCause());
      }
      captionsInfo.append("Caption ID: ").append(caption.getId())
                  .append(", Language: ").append(caption.getSnippet().getLanguage())
                  .append(", Name: ").append(caption.getSnippet().getName())
                  .append(", Legenda: ").append(resultStream.toString()).append("\n");

    });
    return captionsInfo.toString();

  }

  private List<Video> getVideosFromPlaylist(final String playlistId) throws Exception {

    final YouTube.PlaylistItems.List request = youtubeService.playlistItems().list("id,snippet");
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

      final YouTube.Videos.List videoRequest = youtubeService.videos().list(videoId);
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
