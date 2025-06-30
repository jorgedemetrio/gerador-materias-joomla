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
public class CPFValidator implements ConstraintValidator<CPF, String> {
    @Override
    public void initialize(final CPF constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String cpf, final ConstraintValidatorContext context) {
        if (isNull(cpf) || cpf.isBlank()) {
            return true;
        }
        return ValidarDadosUtils.isCPF(cpf);
    }

}
