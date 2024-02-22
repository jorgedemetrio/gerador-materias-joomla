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
public class AtributosCategoriaJoomlaDTO {

  private Long id;
  private String title;
  private String alias;
  private String note;
  private Integer published;
  private Integer access;

  @JsonProperty("checked_out")
  private String checkedOut;

  @JsonProperty("checked_out_time")
  private String checkedOutTime;

  @JsonProperty("created_user_id")
  private Long createdUserId;

  @JsonProperty("parent_id")
  private Long parentId;
  private Integer level;
  private Integer lft;
  private Integer rgt;
  private String language;
  private String description;

  @JsonProperty("language_title")
  private String languageTitle;

  @JsonProperty("language_Image")
  private String languageImage;
  private String editor;

  @JsonProperty("accessLevel")
  private String access_level;

  @JsonProperty("author_name")
  private String authorName;

  @JsonProperty("count_trashed")
  private Integer countTrashed;

  @JsonProperty("count_unpublished")
  private Integer countUnpublished;

  @JsonProperty("count_published")
  private Integer countPublished;

  @JsonProperty("count_archived")
  private Integer countArchived;
}
