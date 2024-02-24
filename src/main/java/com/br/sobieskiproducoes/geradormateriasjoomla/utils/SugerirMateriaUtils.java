/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.utils;

import static java.util.Objects.isNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import org.springframework.util.StreamUtils;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 13:31:54
 * @version 1.0.0
 */
public class SugerirMateriaUtils {

  public static Pattern ENTER = Pattern.compile("\\n");
  public static Pattern ASPAS_DUPLAS = Pattern.compile("\\\"");
  public static final Pattern ASPAS_SIMPLES = Pattern.compile("\\'");

  public static String limparTexto(final String in) throws IOException {
    if (isNull(in) || in.isBlank()) {
      return null;
    }
    return StreamUtils.copyToString(new ByteArrayInputStream(ENTER.matcher(// REMOVE O ENTER FALSO
        ASPAS_DUPLAS.matcher(// REMOVE AS ASPAS FALSAS
            ASPAS_SIMPLES.matcher( // REMOVE AS ASPAS FALSAS
                in).replaceAll("'"))
            .replaceAll("\""))
        .replaceAll("\n").getBytes()), StandardCharsets.UTF_8);

  }
}
