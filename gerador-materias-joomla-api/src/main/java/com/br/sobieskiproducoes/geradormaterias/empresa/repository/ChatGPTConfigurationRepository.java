/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.ChatGPTConfigurationEntity;

/**
 *
 * @author JorgeDemetrioPC
 * @since 2 de jul. de 2025 23:06:24
 * @version 1.0.2 de jul. de 2025
 */
public interface ChatGPTConfigurationRepository extends JpaRepository<ChatGPTConfigurationEntity, String> {

}
