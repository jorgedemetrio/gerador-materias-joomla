/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.validation;

import static java.util.Objects.isNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * @author JorgeDemetrioPC
 * @since 29 de jun. de 2025 12:57:10
 * @version 1.0.29 de jun. de 2025
 */
public class CNPJValidator implements ConstraintValidator<CNPJ, String> {
    @Override
    public void initialize(final CNPJ constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String cnpj, final ConstraintValidatorContext context) {
        if (isNull(cnpj) || cnpj.isBlank()) {
            return true;
        }
        return ValidarDadosUtils.isCNPJ(cnpj);
    }

}
