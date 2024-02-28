/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.MapaPerguntaEntity;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 13:49:24
 * @version 1.0.0
 */
public interface MapaPerguntaRepository extends JpaRepository<MapaPerguntaEntity, Long> {

  List<MapaPerguntaEntity> findByUuid(@Param("uuid") String uuid);
}
