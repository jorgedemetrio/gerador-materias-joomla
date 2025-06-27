/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class AtributosArtigoSalvoJoomlaDTO {
    private String typeAlias;
    private Long id;
    private Long asset_id;
    private String title;
    private String alias;
    private Long state;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private Long created_by;
    private String created_by_alias;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified;
    private Long modified_by;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publish_up;

    private Integer version;
    private String metakey;
    private String metadesc;
    private Integer access;
    private Integer hits;

    private Integer featured;
    private String language;
    private String note;

    private String text;

    /*
     * "metadata": [], "tags": [], "featured_up": null, "featured_down": null,
     * "publish_down": null, "images": [], "urls": [],
     */

}