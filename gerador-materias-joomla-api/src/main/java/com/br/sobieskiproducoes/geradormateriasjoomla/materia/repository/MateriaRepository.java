/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:52:27
 * @version 1.0.0
 */
@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
  CategoriaEntity findByIdJoomla(@Param("idJoomla") Long idJoomla);
}
