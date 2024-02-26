/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto;

import java.time.LocalDate;
import java.util.List;

import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.RequisitaPerguntasDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 07:24:12
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequisicaoCaragMassaDTO {

  @NotNull(message = "O campo \"ideias\" não deveria vir vazio.")
  private RequisitaPerguntasDTO ideias;

  @NotNull(message = "O campo \"data-inicio-publicacao\" não deve ser vazio e deve ser igual ou maior que agora. ")
  @FutureOrPresent(message = "O csampo \"data-inicio-publicacao\" deve ser igual ou maior que agora.")
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonProperty("data-inicio-publicacao")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataInicioPublicacao;

  @Future(message = "O csampo \"data-fim-publicacao\" deve ser  que agora.")
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonProperty("data-fim-publicacao")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataFimPublicacao;


  @Size(min = 1, max = 3, message = "Horas devem pelo menos um.")
  private List<HorarioRequisiscaoDTO> hoarios;

  @JsonProperty(defaultValue = "true")
  private Boolean publicar = Boolean.TRUE;

}
