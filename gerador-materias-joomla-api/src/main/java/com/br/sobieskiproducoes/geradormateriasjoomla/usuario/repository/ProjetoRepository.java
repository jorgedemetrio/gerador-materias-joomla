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

import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.controller.DTO.UsuarioDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.usuario.model.ProjetoEntity;

/**
 * @author Ane Batista
 * @since 8 de abr. de 2024 16:03:42
 * @version 1.0.0
 * 
 */
@Repository
public interface ProjetoRepository extends JpaRepository<ProjetoEntity, Long> {
	
	@Query(name="ProjetoRepository.FindById", value = " SELECT n FROM ProjetoEntity AS n WHERE nome")
	Page<ProjetoEntity> buscaPorNome(@Param("nome") UsuarioDTO nome, Pageable page);
	
	Optional<ProjetoEntity> findByIdUsuario(@Param("idUsuario") UsuarioDTO idUsuario);
}
