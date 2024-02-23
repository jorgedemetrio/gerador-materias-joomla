package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class SugerirMateriaDTO {

  @NotBlank
  private String tema;

  @NotEmpty
  private List<String> termos;

  @NotNull
  @FutureOrPresent
  private LocalDateTime publicar;

  private Long categoria;

}
