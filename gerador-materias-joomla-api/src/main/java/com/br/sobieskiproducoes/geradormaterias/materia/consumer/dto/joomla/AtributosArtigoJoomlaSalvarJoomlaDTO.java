/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtributosArtigoJoomlaSalvarJoomlaDTO {

    private Long id;

    private String alias;
    private String articletext;
    private String catid;
    private String language;
    private String metadesc;
    private String metakey;
    private String title;

    private String introtext;

    private String typeAlias;

    @JsonProperty("asset_id")
    private Long assetId;
    private Integer state;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;// ": "2023-02-06 00:27:13",

    @JsonProperty("created_by")
    private DadosJoomlaDTO createdBy;

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

    private ImagesMateriaJoomlaDTO images;

    private UrlJoomlaDTO urls;
    private Integer version;
    private Integer access;
    private Long hits;
    private MetadataJoomlaDTO metadata;

    private Integer featured;

    private String note;
    private List<String> tags;

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

    private String fulltext;

    private RelationshipJoomlaDTO relationships;

    @JsonProperty("modified_by")
    private UsuarioJoomlaDTO modifiedBy;

    private String category;
    private DadosJoomlaDTO data;

}