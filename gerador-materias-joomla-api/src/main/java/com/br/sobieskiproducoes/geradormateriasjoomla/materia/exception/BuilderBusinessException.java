/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 15:40:56
 * @version 1.0.0
 */
public class BuilderBusinessException {
  private Class<? extends BusinessException> classe = null;
  private String mensagem = null;
  private String codigo = null;
  private Throwable cause = null;

  public BuilderBusinessException() {

  }

  @SuppressWarnings("unchecked")
  public <T extends BusinessException> T builder() {
    if (isNull(classe)) {
      throw new RuntimeException("Defina o tipo de Expcetion ");
    }
    T retorno = null;
    try {
      if (nonNull(mensagem) && nonNull(cause)) {
        final Constructor<? extends BusinessException> constructor = classe.getConstructor(String.class,
            Throwable.class);
        retorno = (T) constructor.newInstance(mensagem, cause);
      } else if (isNull(mensagem)) {
        final Constructor<? extends BusinessException> constructor = classe.getConstructor(Throwable.class);
        retorno = (T) constructor.newInstance(cause);
      } else if (isNull(cause)) {
        final Constructor<? extends BusinessException> constructor = classe.getConstructor(String.class);
        retorno = (T) constructor.newInstance(mensagem);
      } else {
        final Constructor<? extends BusinessException> constructor = classe.getConstructor();
        retorno = (T) constructor.newInstance();
      }

    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      throw new RuntimeException("Erro ao criar instancai da Exception", e);
    }
    retorno.setCodigo(codigo);
    return retorno;
  }

  public BuilderBusinessException cause(final Throwable cause) {
    this.cause = cause;
    return this;
  }

  public BuilderBusinessException classe(final Class<? extends BusinessException> classe) {
    this.classe = classe;
    return this;
  }

  public BuilderBusinessException codigo(final String codigo) {
    this.codigo = codigo;
    return this;
  }

  public BuilderBusinessException mensagem(final String mensagem) {
    this.mensagem = mensagem;
    return this;
  }

}
