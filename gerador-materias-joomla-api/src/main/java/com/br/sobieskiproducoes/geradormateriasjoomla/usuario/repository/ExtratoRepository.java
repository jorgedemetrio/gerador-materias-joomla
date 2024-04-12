/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.usuario.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO.UsuarioDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model.ExtratoEntity;

/**
 * @author Ane Batista
 * @since 8 de abr. de 2024 16:35:39
 * @version 1.0.0
 * 
 */
@Repository
public interface ExtratoRepository extends JpaRepository<ExtratoEntity, Long>{

	@Query(name="ExtratoRepository.FindByIdUsuario", value = " SELECT u FROM GastosEntity AS u WHERE usuario")
	Page<ExtratoEntity> buscaPorUsuario(@Param("usuario") UsuarioDTO usuario, Pageable page);
	
	ExtratoEntity findByIdUsuario(@Param("IdUsuario") UsuarioDTO IdUsuario);
}
