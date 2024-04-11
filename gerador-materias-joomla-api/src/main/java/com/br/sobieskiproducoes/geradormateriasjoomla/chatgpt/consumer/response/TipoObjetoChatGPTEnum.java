/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Jorge Demetrio
 * @since 10 de abr. de 2024 23:17:29
 * @version 1.0-10 de abr. de 2024
 */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoObjetoChatGPTEnum {
  
  
  
  THREAD("thread"),
  THREAD_RUN("thread.run"),
  LIST("list"),
  THREAD_DELETED("thread.deleted"),
  THREAD_RUN_STEP("thread.run.step"),
  THREAD_MESSAGE("thread.message");
  
  private final String valor;
  
  TipoObjetoChatGPTEnum(final String valorP) {
    this.valor = valorP;
  }

  @JsonValue
  @Override
  public String toString() {
    return this.valor;
  }

}
