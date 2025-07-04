/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.EmpresaEntity;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 02:11:58
 * @version 1.0.27 de jun. de 2025
 */
@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, String> {

    @Query("""
             SELECT e FROM EmpresaEntity AS e \
                 JOIN e.usuarios AS u \
             WHERE \
                 u.usuario = :username  AND \
                 e.statusDado not in (StatusEnum.REMOVIDO) AND \
                 e.principal = true
            """)
    Optional<EmpresaEntity> buscarPrincipalPorUsuario(@Param("username") String username);

    @Query("""
             SELECT e FROM EmpresaEntity AS e \
                 JOIN e.usuarios AS u \
             WHERE \
                 u.id = :idUsuario  AND \
                 e.statusDado not in (StatusEnum.REMOVIDO) AND \
                 e.principal = true
            """)
    Optional<EmpresaEntity> empresaUsuario(@Param("idEmpresa") String idEmpresa, @Param("idUsuario") String idUsuario);

    @Query("""
             SELECT e FROM EmpresaEntity AS e \
                 JOIN e.usuarios AS u \
             WHERE \
                 u.usuario = :username  AND \
                 e.statusDado not in (StatusEnum.REMOVIDO) \
            """)
    List<EmpresaEntity> buscarPorUsuario(@Param("username") String username);
}
