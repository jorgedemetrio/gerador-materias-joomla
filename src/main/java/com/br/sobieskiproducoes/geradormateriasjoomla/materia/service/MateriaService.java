/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvert;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:06
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class MateriaService {

  private final MateriaRepository materiaRepository;

  private final MateriaConvert convert;

}
