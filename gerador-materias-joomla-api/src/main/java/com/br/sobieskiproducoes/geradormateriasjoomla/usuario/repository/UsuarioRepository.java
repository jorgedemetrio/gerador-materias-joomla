/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model.UsuarioEntity;

/**
 * @author Ane Batista
 * @since 8 de abr. de 2024 15:40:15
 * @version 1.0.0
 * 
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	
	@Query(name="UsuarioRepository.FindByIdUSuario", value = " SELECT u FROM UsuarioEntity AS u WHERE usuario")
	Page<UsuarioEntity> buscaPorUsuario(@Param("usuario") String usuario, Pageable page);
	
	Optional<UsuarioEntity> findByIdUsuario(@Param("idUsuario") Long idUsuario);
	
}
