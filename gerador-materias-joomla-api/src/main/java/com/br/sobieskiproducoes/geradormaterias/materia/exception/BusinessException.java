/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.exception;

import lombok.Getter;
import lombok.Setter;



/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 15:06:30
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class BusinessException extends Exception {

  /**
   *
   */
  private static final long serialVersionUID = 1445691657890047372L;

  public static BuilderBusinessException build() {
    return new BuilderBusinessException();
  }

  private String codigo = null;

  public BusinessException() {
  }
  public BusinessException(final String message) {
    super(message);
  }

  public BusinessException( final String  m, final Throwable t) {
    super(m,t);
  }

  public BusinessException(final Throwable t) {
    super(t);
  }
}