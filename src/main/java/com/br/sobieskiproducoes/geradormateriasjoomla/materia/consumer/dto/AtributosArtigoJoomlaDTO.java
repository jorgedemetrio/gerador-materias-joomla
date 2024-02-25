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

  @JsonProperty("modified_by")
  private Long modifiedBy;

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

  private UsuarioDTO created_by;

  private UsuarioDTO modified_by;

}