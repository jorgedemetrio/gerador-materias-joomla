/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.mapaperguntas.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.model.MapaPerguntaEntity;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 13:49:24
 * @version 1.0.0
 */
public interface MapaPerguntaRepository extends JpaRepository<MapaPerguntaEntity, Long> {

//  @Query(name = "MapaPerguntaRepository.buscaPorUiidParaCargaLimitado5",
//      value = "SELECT m FROM MapaPerguntaEntity AS m WHERE m.uuid = :uuid AND (m.materias IS NULL OR m.materias IS EMPTY) ORDER BY m.id LIMIT 5")

  @Query(name = "MapaPerguntaRepository.buscaPorUiidParaCargaLimitado5", value = "SELECT * FROM tbl_mapa_perguntas AS m WHERE m.uuid_requisicao = :uuid "
      + " AND m.id NOT IN (SELECT id_mapa_pergunta FROM tbl_materia WHERE uuid_requisicao = :uuid) ORDER BY m.id LIMIT 5", nativeQuery = true)
  Collection<MapaPerguntaEntity> buscaPorUiidParaCargaLimitado5(@Param("uuid") String uuid);

  @Query(name = "MapaPerguntaRepository.totalAProcessar", value = "SELECT count(1) FROM tbl_mapa_perguntas AS m "
      + " WHERE uuid_requisicao =:uuid AND id NOT IN (SELECT id_mapa_pergunta FROM tbl_materia)", nativeQuery = true)
  Long totalAProcessar(@Param("uuid") String uuid);
}
