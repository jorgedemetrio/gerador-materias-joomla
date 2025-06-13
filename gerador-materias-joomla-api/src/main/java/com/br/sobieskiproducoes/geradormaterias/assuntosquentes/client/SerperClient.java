/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.assuntosquentes.client;

import org.springframework.stereotype.Component;

import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 13 de jun. de 2025 20:24:50
 */
@Log
@RequiredArgsConstructor
@Component
public class SerperClient {

  private final ConfiguracoesProperties properties;

}
