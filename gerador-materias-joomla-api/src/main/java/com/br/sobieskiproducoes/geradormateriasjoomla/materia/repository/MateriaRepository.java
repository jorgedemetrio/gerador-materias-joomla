/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:52:27
 * @version 1.0.0
 */
@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {

  @Query(name = "MateriaRepository.buscarMateriasPublicar", value = """
      SELECT m FROM MateriaEntity AS m WHERE m.idJoomla is null \
       AND m.uuid is not null AND m.peguntaPrincipal is not null \
       AND m.status = StatusProcessamentoEnum.PROCESSAR \
       AND m.materia IS NOT NULL \
       AND m.idJoomla IS NULL \
       AND (m.publicar >= now() OR m.publicar is null) ORDER BY m.publicar
       LIMIT 15 """)
  List<MateriaEntity> buscarMateriasPublicar();

  @Query(name = "MateriaRepository.buscarPorPergunta", value = "SELECT m FROM MateriaEntity AS m JOIN m.peguntaPrincipal AS p WHERE p.id = :id")
  Optional<MateriaEntity> buscarPorPergunta(@Param("id") Long id);


  Optional<MateriaEntity> findByIdJoomla(@Param("idJoomla") Long idJoomla);

  @Query(name = "MateriaRepository.buscarMateriaVazias", value = "SELECT m FROM MateriaEntity AS m WHERE m.materia IS NULL ")
  Page<MateriaEntity> buscarMateriaVazias(Pageable page);

  @Query(name = "MapaPerguntaRepository.totalAProcessar", value = "SELECT max(data_publicar) FROM tbl_materia AS m "
      + " WHERE uuid_requisicao =:uuid AND TIME_FORMAT(data_publicar, '%H:%i') = :hora", nativeQuery = true)
  LocalDateTime ultimaData(@Param("uuid") String uuid, @Param("hora") String hora);

}
