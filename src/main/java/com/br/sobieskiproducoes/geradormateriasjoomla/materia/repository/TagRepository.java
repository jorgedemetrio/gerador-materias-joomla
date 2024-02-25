/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.TagEntity;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:52:27
 * @version 1.0.0
 */
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
  TagEntity findByIdJoomla(@Param("idJoomla") Long idJoomla);

  List<TagEntity> findByTitulo(@Param("titulo") String titulo);

  Page<TagEntity> findByTituloContainingIgnoreCase(@Param("titulo") String titulo,
      Pageable pageable);
}
