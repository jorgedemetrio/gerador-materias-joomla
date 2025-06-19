/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormaterias.usuario.model.GrupoEntity;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 02:32:07
 */
@Repository
public interface GrupoRepository extends JpaRepository<GrupoEntity, String> {

  List<GrupoEntity> findByUsuariosIn(@Param("usuarios") String usuarios);
}
