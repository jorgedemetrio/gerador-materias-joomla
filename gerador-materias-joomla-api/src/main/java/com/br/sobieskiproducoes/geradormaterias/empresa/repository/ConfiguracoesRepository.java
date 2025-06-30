/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormaterias.empresa.model.ConfiguracoesEntity;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 24 de jun. de 2025 23:54:20
 */
@Repository
public interface ConfiguracoesRepository extends JpaRepository<ConfiguracoesEntity, Long> {

    @Query(name = "ConfiguracoesRepository.buscaConfiguracoesComMateriasAPublicar", value = """
             SELECT c FROM ConfiguracoesEntity AS c  JOIN c.materias AS m WHERE m.uuid is not null AND m.peguntaPrincipal is not null \
            AND m.status = StatusProcessamentoEnum.PROCESSAR \
            AND m.materia IS NOT NULL \
            AND m.idJoomla IS NULL \
            AND (m.publicar >= now() OR m.publicar is null) """)
    List<ConfiguracoesEntity> buscaConfiguracoesComMateriasAPublicar();

    @Query(name = "ConfiguracoesRepository.buscaConfiguracoes", value = """
            SELECT c FROM ConfiguracoesEntity AS c  \
                    JOIN c.empresa AS e \
                    JOIN e.usuarios AS u \
            WHERE
               c.statusDado not in (StatusEnum.REMOVIDO) AND
               u.usuario = :username \
               """)
    List<ConfiguracoesEntity> buscaConfiguracoes(@Param("username") String username, Pageable page);
}
