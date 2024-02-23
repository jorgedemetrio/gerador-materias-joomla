/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 23 de fev. de 2024 01:18:35
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RetornoBusinessDTO<T> {

  private Long total;

  private Integer totalPaginas;

  private List<T> itens;
}
