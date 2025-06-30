/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author JorgeDemetrioPC
 * @since 29 de jun. de 2025 12:53:20
 * @version 1.0.29 de jun. de 2025
 */
@Size(min = 1, max = 14)
@Pattern(regexp = "^([0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2})|([0-9]{11})$")
@ReportAsSingleViolation
@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface CPF {
    String message() default "CPF inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
