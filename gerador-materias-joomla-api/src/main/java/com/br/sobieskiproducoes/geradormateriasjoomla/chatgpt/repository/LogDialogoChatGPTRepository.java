/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.model.LogDialogoChatGPTEntity;

/**
 * @author Jorge Demetrio
 * @since 23 de fev. de 2024 19:08:49
 * @version 1.0.0
 */
@Repository
public interface LogDialogoChatGPTRepository extends JpaRepository<LogDialogoChatGPTEntity, Long> {

}
