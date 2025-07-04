/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.TermosEmpresaEntity;

/**
 *
 * @author JorgeDemetrioPC
 * @since 3 de jul. de 2025 00:32:02
 * @version 1.0.3 de jul. de 2025
 */
public interface TermosEmpresaRepository extends JpaRepository<TermosEmpresaEntity, String> {

    @Query("""
            SELECT t FROM \
                    TermosEmpresaEntity AS t JOIN \
                    t.empresa AS e JOIN \
                    e.usuarios AS u \
            WHERE \
                    t.statusDado not in (StatusEnum.REMOVIDO) AND \
                    e.id = :idEmpresa AND \
                    u.id = :idUsuario AND \
                    (:nome is null OR  upper(t.nome) like concat('%', upper(trim(:nome)), '%')) \
                """)
    List<TermosEmpresaEntity> consulta(@Param("nome") String nome, @Param("idEmpresa") String idEmpresa, @Param("idUsuario") String idusuario,
            Pageable paginacao);

    @Query("""
            SELECT t FROM \
                    TermosEmpresaEntity AS t JOIN \
                    t.empresa AS e JOIN \
                    e.usuarios AS u \
            WHERE \
                    t.statusDado not in (StatusEnum.REMOVIDO) AND \
                    e.id = :idEmpresa AND \
                    t.id = :idTermo AND \
                    u.id = :idUsuario \
                """)
    Optional<TermosEmpresaEntity> get(@Param("idTermo") String idTermo, @Param("idEmpresa") String idEmpresa, @Param("idUsuario") String idUsuario);

}
