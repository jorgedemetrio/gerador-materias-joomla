/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 20:07:28
 * @version 1.0.0
 */

@EnableBatchProcessing
@SpringBootApplication
public class Application {
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
