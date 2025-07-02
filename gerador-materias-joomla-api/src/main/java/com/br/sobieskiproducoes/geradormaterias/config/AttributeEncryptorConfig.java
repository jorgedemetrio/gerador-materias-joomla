/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 18:35:35
 * @version 1.0.26 de jun. de 2025
 */
@Converter
public class AttributeEncryptorConfig implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES";

    @Value("${configuracao.chave-campo-banco}")
    private String ENCRYPTION_KEY; // Substitua por uma chave segura

    @Override
    public String convertToDatabaseColumn(final String attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            final byte[] encryptedBytes = cipher.doFinal(attribute.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey());
            final byte[] decodedBytes = Base64.getDecoder().decode(dbData);
            final byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey getKey() throws Exception {
        // Gerar chave secreta (melhor usar um KeyGenerator para chaves aleatórias)
        // KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        // keyGenerator.init(128); // Tamanho da chave
        // SecretKey secretKey = keyGenerator.generateKey();
        // return secretKey;

        // Substitua por geração segura de chave
        return new javax.crypto.spec.SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
    }
}
