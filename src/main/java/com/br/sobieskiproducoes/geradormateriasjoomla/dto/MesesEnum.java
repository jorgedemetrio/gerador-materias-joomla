/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 16:33:47
 * @version 1.0.0
 */
@JsonFormat
public enum MesesEnum {

  JANEIRO("Janeiro", "janeiro"), FEVEREIRO("Fevereiro", "fevereiro"), MARCO("Março", "marco"), ABRIL("Abril", "abril"),
  MAIO("Maio", "maio"), JUNHO("Junho", "junho"), JULHO("Julho", "julho"), AGOSTO("Agosto", "agosto"),
  SETEMBRO("Setembro", "setembro"), OUTUBRO("Outubro", "outubro"), NOVEMBRO("Novembro", "novembro"),
  DEZEMBRO("Dezembro", "dezembro");

  @JsonCreator
  public static MesesEnum fromValue(final String value) {
    for (final MesesEnum mes : MesesEnum.values()) {
      if (mes.getNomeMaiusculo().equalsIgnoreCase(value) || mes.getNomeMinusculo().equalsIgnoreCase(value)) {
        return mes;
      }
    }
    return null;
  }
  private final String nomeMaiusculo;

  private final String nomeMinusculo;

  MesesEnum(final String nomeMaiusculo, final String nomeMinusculo) {
    this.nomeMaiusculo = nomeMaiusculo;
    this.nomeMinusculo = nomeMinusculo;
  }

  public boolean equals(final MesesEnum item) {
    return getNomeMinusculo().equalsIgnoreCase(item.getNomeMinusculo());
  }

  public boolean equals(final String item) {
    if (item == null || item.isBlank()) {
      return false;
    }
    return getNomeMaiusculo().equalsIgnoreCase(item.trim()) || getNomeMinusculo().equalsIgnoreCase(item.trim());
  }

  public String getNomeMaiusculo() {
    return nomeMaiusculo;
  }

  @JsonValue
  public String getNomeMinusculo() {
    return nomeMinusculo;
  }

  @Override
  public String toString() {
    return nomeMinusculo;
  }
}
