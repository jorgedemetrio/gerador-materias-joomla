/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import java.time.format.DateTimeFormatter;

/**
 * @author Jorge Demetrio
 * @since 23 de fev. de 2024 01:10:28
 * @version 1.0.0
 */
public class MateriaConstants {
  public static final Integer MAX_INTENS_PER_PAGE = 30;
  public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  public static final String SEM_IDIOMA = "*";
}
