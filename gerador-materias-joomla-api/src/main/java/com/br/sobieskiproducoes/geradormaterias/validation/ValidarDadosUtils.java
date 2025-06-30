/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.validation;

import java.util.InputMismatchException;

/**
 *
 * @author JorgeDemetrioPC
 * @since 29 de jun. de 2025 12:55:08
 * @version 1.0.29 de jun. de 2025
 */
public class ValidarDadosUtils {
    public static boolean isCPF(final String CPF) {
        // considera-se erro CPF"s formados por uma sequencia de numeros iguais
        if ("00000000000".equals(CPF) || "11111111111".equals(CPF) || "22222222222".equals(CPF) || "33333333333".equals(CPF) || "44444444444".equals(CPF)
                || "55555555555".equals(CPF) || "66666666666".equals(CPF) || "77777777777".equals(CPF) || "88888888888".equals(CPF) || "99999999999".equals(CPF)
                || CPF.length() != 11) {
            return false;
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere "0" no inteiro 0
                // (48 eh a posicao de "0" na tabela ASCII)
                num = CPF.charAt(i) - 48;
                sm = sm + num * peso;
                peso = peso - 1;
            }

            r = 11 - sm % 11;
            if (r == 10 || r == 11) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + num * peso;
                peso = peso - 1;
            }

            r = 11 - sm % 11;
            if (r == 10 || r == 11) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if (dig10 == CPF.charAt(9) && dig11 == CPF.charAt(10)) {
                return true;
            }
            return false;
        } catch (final InputMismatchException erro) {
            return false;
        }
    }

    public static boolean isCNPJ(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Verifica se o CNPJ possui 14 dígitos
        // Verifica se todos os dígitos são iguais (CNPJ inválido)
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        final int primeiroDigito = calcularDigitoVerificador(cnpj.substring(0, 12), 1);

        // Calcula o segundo dígito verificador
        final int segundoDigito = calcularDigitoVerificador(cnpj.substring(0, 12) + primeiroDigito, 2);

        // Verifica se os dígitos calculados correspondem aos dígitos do CNPJ
        return String.valueOf(primeiroDigito).equals(cnpj.substring(12, 13)) && String.valueOf(segundoDigito).equals(cnpj.substring(13, 14));
    }

    private static int calcularDigitoVerificador(final String cnpjBase, final int digito) {
        final int[] pesos = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
        int soma = 0;

        for (int i = 0; i < cnpjBase.length(); i++) {
            soma += Integer.parseInt(String.valueOf(cnpjBase.charAt(i))) * pesos[i + 12 - cnpjBase.length()];
        }

        final int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

}
