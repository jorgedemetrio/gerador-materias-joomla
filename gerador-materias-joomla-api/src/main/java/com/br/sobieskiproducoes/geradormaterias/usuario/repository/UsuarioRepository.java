/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 02:31:25
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {

  UsuarioEntity findByUsuarioIgnoreCase(@Param("usuario") String usuario);
}
