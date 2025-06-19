/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.chatgpt.dto;

import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 18 de jun. de 2025 23:33:23
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Scope("session")
public class SessaoChatGPTDTO {
  private ConfiguracoesEntity configuracao;
  private String threadId;
  private String runnerId;
}
