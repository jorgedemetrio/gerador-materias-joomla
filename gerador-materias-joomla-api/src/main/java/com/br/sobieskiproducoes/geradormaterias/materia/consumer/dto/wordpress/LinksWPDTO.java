package com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.wordpress;

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
public class LinksWPDTO {
  public ArrayList<SelfWPDTO> self;

  public ArrayList<HrefWPDTO> collection;
  public ArrayList<HrefWPDTO> about;

  @JsonProperty("wp:post_type")
  public ArrayList<HrefWPDTO> wpPostType;

  @JsonProperty("predecessor-version")
  public ArrayList<HrefWPDTO> predecessorVersion;

  public ArrayList<HrefWPDTO> author;
  public ArrayList<HrefWPDTO> replies;

  @JsonProperty("version-history")
  public ArrayList<HrefWPDTO> versionHistory;

  @JsonProperty("wp:attachment")
  public ArrayList<HrefWPDTO> wpAttachment;

  @JsonProperty("wp:term")
  public ArrayList<HrefWPDTO> wpTerm;
  public ArrayList<HrefWPDTO> curies;
}
