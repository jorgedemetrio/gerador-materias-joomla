/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert;

import org.mapstruct.Mapper;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.MateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:59
 * @version 1.0.0
 */
@Mapper
public interface MateriaConvert {

  MateriaEntity convert(MateriaDTO materia);

  MateriaDTO convert(MateriaEntity materia);
}
