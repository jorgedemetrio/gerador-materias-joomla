package com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.wordpress;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostWPDTO {
  public Long id;
  public LocalDateTime date;

  @JsonProperty("date_gmt")
  public LocalDateTime dateGmt;
  public GuidWPDTO guid;
  public LocalDateTime modified;

  @JsonProperty("modified_gmt")
  public LocalDateTime modifiedGmt;
  public String slug;
  public String status;
  public String type;
  public String link;
  public TitleWPDTO title;
  public ContentWPDTO content;
  public ContentWPDTO excerpt;
  public Long author;
  @JsonProperty("featured_media")
  public Long featuredMedia;

  @JsonProperty("comment_status")
  public String commentStatus;

  @JsonProperty("ping_status")
  public String pingStatus;
  public Boolean sticky;
  public String template;
  public String format;
  public MetaWPDTO meta;
  public ArrayList<Long> categories;
  public ArrayList<Long> tags;

  @JsonProperty("class_list")
  public ArrayList<String> classList;

  @JsonProperty("_links")
  public LinksWPDTO links;
}
