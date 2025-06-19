/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 02:01:48
 */
public enum NivelUsuarioEnum {
  ADMONISTRADOR("A"), NORMAL("N");

  private final String tipo;

  NivelUsuarioEnum(final String tipoP) {
    tipo = tipoP;
  }

  @JsonValue
  @Override
  public String toString() {
    return tipo;
  }

}
