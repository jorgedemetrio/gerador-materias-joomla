/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.springframework.util.StreamUtils;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 13:31:54
 * @version 1.0.0
 */
public class SugerirMateriaUtils {

  public static final Pattern ENTER = Pattern.compile("\\n");
  public static final Pattern ASPAS_DUPLAS = Pattern.compile("\\\"");
  public static final Pattern ASPAS_SIMPLES = Pattern.compile("\\'");

  public static final Pattern HORA_SIMPLES = Pattern.compile("[0-9]{2}:[0-9]{2}");
  public static final Pattern HORA_COM_SEGUNDO = Pattern.compile("[0-9]{2}:[0-9]{2}::[0-9]{2}");

  public static long getDaysBetween(final LocalDate startDate, final LocalDate endDate) {
    return startDate.until(endDate, java.time.temporal.ChronoUnit.DAYS);
  }

  public static LocalDateTime getLocalDateTime(final LocalDate date, final String time) {
    final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    final LocalTime localTime = LocalTime.parse(HORA_COM_SEGUNDO.matcher(time).find() ? time : time.concat(":00"),
        timeFormatter);
    return LocalDateTime.of(date, localTime);
  }

  public static LocalDateTime getLocalDateTime(final LocalDate date, final String time, final long dias) {
    return getLocalDateTime(date.plusDays(dias), time);
  }

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

  public static String normalizeText(final String text) {
    String normalized = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^a-zA-Z0-9\\s-]", "");
    normalized = normalized.replaceAll("\\s", "-");
    normalized = normalized.toLowerCase();
    return normalized.replaceAll("^[-]+|[-]+$", "");
  }

  public static String pathCategoria(final CategoriaEntity categoria) throws IOException {
  return "/"
      .concat(nonNull(categoria.getApelido()) || !categoria.getApelido().isBlank() ? categoria.getApelido()
          : limparTexto(categoria.getTitulo()))
      .concat(nonNull(categoria.getPai()) ? pathCategoria(categoria.getPai()) : "");
}
  /**
   * Não permite a criação de uma instância nova.
   */
  private SugerirMateriaUtils() {}
}
