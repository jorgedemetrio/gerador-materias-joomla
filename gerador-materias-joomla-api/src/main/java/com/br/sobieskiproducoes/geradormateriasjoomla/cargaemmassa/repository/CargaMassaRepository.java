/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.model.CargaMassaEntity;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 20:20:28
 * @version 1.0.0
 */
@Repository
public interface CargaMassaRepository extends JpaRepository<CargaMassaEntity, Long> {

  @Query(name = "CargaMassaRepository.pegarCarga", value = "SELECT c FROM CargaMassaEntity AS c WHERE c.status != StatusProcessamentoEnum.PROCESSADO ")
  List<CargaMassaEntity> pegarCarga();

}
