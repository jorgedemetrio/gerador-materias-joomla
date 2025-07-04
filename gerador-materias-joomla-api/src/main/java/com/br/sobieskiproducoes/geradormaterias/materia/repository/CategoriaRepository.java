/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormaterias.materia.model.CategoriaEntity;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:52:27
 * @version 1.0.0
 */
@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    @Query(" SELECT c FROM CategoriaEntity AS c WHERE UPPER(c.titulo) like concat('%', :titulo, '%') ")
    Page<CategoriaEntity> buscaPorTitulo(@Param("titulo") String titulo, Pageable page);

    @Cacheable("categoriasParaPrompt")
    @Query(" SELECT c FROM CategoriaEntity AS c WHERE c.usarEmPrompts = true ")
    List<CategoriaEntity> categoriasParaPrompt();

    Optional<CategoriaEntity> findByIdJoomla(@Param("idJoomla") Long idJoomla);

}
