/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 14:21:37
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
public class AtributosArtigoJoomlaDTO {

  private Long id;

  private String alias;
  private String articletext;
  private Long catid;
  private String language;
  private String metadesc;
  private String metakey;
  private String title;
  private Long condition;

  private String typeAlias;

  @JsonProperty("asset_id")
  private Long assetId;
  private Long state;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime created;// ": "2023-02-06 00:27:13",

  @JsonProperty("created_by")
  private Long createdBy;

  @JsonProperty("created_by_alias")
  private String createdByAlias;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime modified;



  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonProperty("publish_up")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime publishUp;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonProperty("publish_down")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime publishDown;

  private ImagesMateriaDTO images;

  private UrlDTO urls;
  private Long version;
  private Long access;
  private Long hits;
  private MetadataDTO metadata;

  private Long featured;

  private String note;
  private Map<String, String> tags;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonProperty("featured_up")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime featuredUp;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonProperty("featured_down")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime featuredDown;

  private String text;

  private RelationshipDTO relationships;


  @JsonProperty("modified_by")
  private UsuarioDTO modifiedBy;
  
  @JsonProperty("created_by_alias")
  private String createdBvyAlias;
  
  @JsonProperty("publish_up")
  private String publish_up;
  
  @JsonProperty("publish_down")
  private String publish_down;

  @JsonProperty("image_intro")
  private String imageIntro;
  
  @JsonProperty("image_intro_alt")
  private String imageIntroAlt;
  
  @JsonProperty("float_intro")
  private String floatIntro;
  
  @JsonProperty("image_intro_caption")
  private String imageIntroCaption;
  
  @JsonProperty("image_fulltext")
  private String imageFulltext;
  
  @JsonProperty("image_fulltext_alt")
  private String imageFulltextAlt;
  
  @JsonProperty("float_fulltext")
  private String floatFulltext;
  
  @JsonProperty("image_fulltext_caption")
  private String imageFulltextCaption;


  private String robots;
  private String author;
  private String rights;
  
  @JsonProperty("featured_up")
  private String featured_up;
  
  @JsonProperty("featured_down")
  private String featured_down;


  private String category;
  private String data;
  private String type;



  private String urla;
  private String urlatext;
  private String targeta;
  private String urlb;
  private String urlbtext;
  private String targetb;
  private String urlc;
  private String urlctext;
  private String targetc;

}