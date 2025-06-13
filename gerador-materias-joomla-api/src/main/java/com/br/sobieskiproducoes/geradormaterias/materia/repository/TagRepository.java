/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormaterias.materia.model.TagEntity;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:52:27
 * @version 1.0.0
 */
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

  @Query(name = "TagRepository.bucarPorApelidoTitulo", value = "SELECT t FROM TagEntity AS t WHERE UPPER(TRIM(t.titulo)) = UPPER(TRIM(:titulo)) OR "
      + " UPPER(TRIM(t.apelido)) = UPPER(TRIM(:apelido)) ")
  Optional<TagEntity> buscarPorApelidoTitulo(@Param("apelido") String apelido,
      @Param("titulo") String titulo);

  TagEntity findByIdJoomla(@Param("idJoomla") Long idJoomla);

  List<TagEntity> findByTitulo(@Param("titulo") String titulo);

  Page<TagEntity> findByTituloContainingIgnoreCase(@Param("titulo") String titulo, Pageable pageable);

}
