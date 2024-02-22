/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:59:58
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LinkResponse {

  private String self;
  private String next;
  private String last;
  private String first;

}
