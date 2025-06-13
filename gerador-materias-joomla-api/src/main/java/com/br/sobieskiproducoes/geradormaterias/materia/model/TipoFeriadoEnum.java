/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 8 de abr. de 2025 16:35:30
 */

public enum TipoFeriadoEnum {
  FERIADO_NORMAL("F"), DATA_COMEMORATIVA("D"), OUTRO("O");

  private final String tipo;

  TipoFeriadoEnum(final String tipoP) {
    tipo = tipoP;
  }

  @JsonValue
  @Override
  public String toString() {
    return tipo;
  }

}
