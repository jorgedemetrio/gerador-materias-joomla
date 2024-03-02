/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository;

import java.util.List;
import java.util.Optional;

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

  @Query(name = "MateriaRepository.buscarMateriasPublicar", value = "SELECT m FROM MateriaEntity AS m JOIN  m.idJoomla is null "
      + " AND m.uuid is not null AND m.peguntaPrincipal is not null AND status = StatusProcessamentoEnum.PROCESSAR AND (m.publicar >= now() OR m.publicar is null) ORDER BY m.publicar")
  List<MateriaEntity> buscarMateriasPublicar();

  @Query(name = "MateriaRepository.buscarPorPergunta", value = "SELECT m FROM MateriaEntity AS m JOIN m.peguntaPrincipal AS p WHERE p.id = :id")
  Optional<MateriaEntity> buscarPorPergunta(@Param("id") Long id);


  MateriaEntity findByIdJoomla(@Param("idJoomla") Long idJoomla);
}
