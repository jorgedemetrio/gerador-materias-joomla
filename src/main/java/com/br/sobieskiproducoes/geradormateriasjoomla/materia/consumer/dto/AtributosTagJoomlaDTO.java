/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class AtributosTagJoomlaDTO {

  private Long id;
  
  private String title;
  
  private String alias;
  
  @JsonProperty("parent_id")
  private Long parentId;
  
  
  private String lft;
  
  private String rgt;
  
  private String path;
  
  private String note;
  
  private String description;
  
  private String published;
  
  @JsonProperty("checked_out")
  private String checkedOut;
  
  @JsonProperty("checked_out_time")
  private String checkedOutTime;
  
  private String access;
  
  private String params;
  
  private String metadesc;
  
  private String metakey;
  
  private String metadata;
  
  @JsonProperty("created_user_id")
  private String createdUserId;
  
  @JsonProperty("created_time")
  private String createdTime;
  
  
  @JsonProperty("created_by_alias")
  private String createdByAlias;
  
  @JsonProperty("modified_user_id")
  private String modifiedUserId;
  
  @JsonProperty("modified_time")
  private String modifiedTime;
  
  private String images;
  
  private String urls;
  
  private String hits;
  
  private String language;
  
  private String version;
  
  @JsonProperty("publish_up")
  private String publishUp;
  
  @JsonProperty("publish_down")
  private String publishDown;
  

  private String checked_out_time;
  
  private String created_user_id;

  private String parent_id;
  
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
