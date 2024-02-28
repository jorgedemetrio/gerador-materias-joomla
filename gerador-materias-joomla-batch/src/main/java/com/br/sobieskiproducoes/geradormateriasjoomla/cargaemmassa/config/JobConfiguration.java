/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.config;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 20:08:26
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Configurable
public class JobConfiguration {

  private final JobBuilder jobBuilderFactory;

  private final StepBuilder stepBuilderFactory;


}
