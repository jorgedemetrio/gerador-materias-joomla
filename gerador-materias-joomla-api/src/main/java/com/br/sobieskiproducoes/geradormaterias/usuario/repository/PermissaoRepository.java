/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormaterias.usuario.model.PermissaoEntity;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 02:32:57
 */
@Repository
public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {

    @Query(name = "PermissaoRepository.findByGruposIn", value = "SELECT g FROM PermissaoEntity AS g JOIN g.grupos AS gru WHERE gru.id = :grupo ")
    List<PermissaoEntity> findByGruposIn(@Param("grupo") String grupo);
}
