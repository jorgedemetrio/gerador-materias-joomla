/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:38:29
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtributosTagJoomlaDTO {

  private Long id;

  private String title;

  private String alias;

  @JsonProperty("parent_id")
  private Long parentId;

  private Long lft;

  private Long rgt;

  private String path;

  private String note;

  private String description;

  private Long published;

  @JsonProperty("checked_out")
  private String checkedOut;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("checked_out_time")
  private LocalDateTime checkedOutTime;

  private Long access;

  private List<String> params;

  private String metadesc;

  private String metakey;

  private MetadataDTO metadata;

  @JsonProperty("created_user_id")
  private Long createdUserId;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("created_time")
  private LocalDateTime createdTime;

  @JsonProperty("created_by_alias")
  private String createdByAlias;

  @JsonProperty("modified_user_id")
  private Long modifiedUserId;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("modified_time")
  private LocalDateTime modifiedTime;

  private List<String> images;

  private UrlDTO urls;

  private Long hits;

  private String language;

  private Long version;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("publish_up")
  private LocalDateTime publishUp;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("publish_down")
  private String publishDown;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime checked_out_time;

  @JsonProperty("language_title")
  private String languageTitle;

  @JsonProperty("language_image")
  private String languageImage;

  private String editor;

  @JsonProperty("author_name")
  private String authorName;

  @JsonProperty("access_title")
  private String accessTitle;

}
