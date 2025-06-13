/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormaterias.materia.model.ProcessamentoLogMateriaEntity;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 9 de jan. de 2025 01:27:01
 */
@Repository
public interface ProcessamentoLogMateriaRepository extends JpaRepository<ProcessamentoLogMateriaEntity, Long> {

}
