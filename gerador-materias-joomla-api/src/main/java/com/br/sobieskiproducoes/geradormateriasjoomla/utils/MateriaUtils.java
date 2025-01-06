/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.util.StreamUtils;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 13:31:54
 * @version 1.0.0
 */
public class MateriaUtils {

  public static final Pattern ENTER = Pattern.compile("\\n");
  public static final Pattern ASPAS_DUPLAS = Pattern.compile("\\\"");
  public static final Pattern ASPAS_SIMPLES = Pattern.compile("\\'");
  public static final Pattern ASPAS_HTML_INICIO = Pattern.compile("```html");
  public static final Pattern ASPAS_HTML = Pattern.compile("```");

  public static final Pattern HORA_SIMPLES = Pattern.compile("\\d{2}:\\d{2}");
  public static final Pattern HORA_COM_SEGUNDO = Pattern.compile("\\d{2}:\\d{2}::\\d{2}");
  public static final Pattern PATTERN_URL = Pattern.compile("(https?://|ftp://|bit\\.ly/\\w+)\\S*", Pattern.CASE_INSENSITIVE);

  public static long getDaysBetween(final LocalDate startDate, final LocalDate endDate) {
    return startDate.until(endDate, java.time.temporal.ChronoUnit.DAYS);
  }

  public static LocalDateTime getLocalDateTime(final LocalDate date, final String time) {
    final var timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    final var localTime = LocalTime.parse(MateriaUtils.HORA_COM_SEGUNDO.matcher(time).find() ? time : time.concat(":00"), timeFormatter);
    return LocalDateTime.of(date, localTime);
  }

  public static LocalDateTime getLocalDateTime(final LocalDate date, final String time, final long dias) {
    return MateriaUtils.getLocalDateTime(date.plusDays(dias), time);
  }

  public static String limparTexto(final String in) throws IOException {
    if (Objects.isNull(in) || in.isBlank()) {
      return null;
    }

    var texto = StreamUtils.copyToString(new ByteArrayInputStream(

        MateriaUtils.ASPAS_HTML_INICIO.matcher(MateriaUtils.ASPAS_HTML.matcher(MateriaUtils.ENTER.matcher(// REMOVE
            // O
            // ENTER
            // FALSO
            MateriaUtils.ASPAS_DUPLAS.matcher(// REMOVE AS ASPAS FALSAS
                MateriaUtils.ASPAS_SIMPLES.matcher( // REMOVE AS ASPAS FALSAS
                    in).replaceAll("'"))
                .replaceAll("\""))
            .replaceAll("\n")).replaceAll("")).replaceAll("").getBytes()),
        StandardCharsets.UTF_8);
    if (texto.toLowerCase().startsWith("html")) {
      texto = texto.substring(4);
    }
    return texto;
  }

  public static String limparTextoJson(final String in) throws IOException {
    if (Objects.isNull(in) || in.isBlank()) {
      return null;
    }
    final var retorno = MateriaUtils.limparTexto(in);

    var indexColchete = retorno.indexOf("[");
    var indexChaves = retorno.indexOf("{");

    final var comecaChaves = indexChaves < indexColchete && indexChaves >= 0;

    final var primeiraPosicao = comecaChaves ? indexChaves : indexColchete;

    indexColchete = retorno.lastIndexOf("]");
    indexChaves = retorno.lastIndexOf("}");

    final var fimPosicao = (indexColchete > indexChaves ? indexColchete : indexChaves) + 1;
    return (primeiraPosicao < 0 ? retorno.trim() : retorno.substring(primeiraPosicao, fimPosicao).trim());

  }

  public static String normalizeText(final String text) {
    var normalized = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^a-zA-Z0-9\\s-]", "");
    normalized = normalized.replaceAll("\\s", "-").replace('ã', 'a').replace('ç', 'c').replace('é', 'e').replace('á', 'a');
    normalized = normalized.toLowerCase();
    return normalized.replaceAll("^[-]+|[-]+$", "");
  }

  public static String pathCategoria(final CategoriaEntity categoria) throws IOException {
    return (Objects.nonNull(categoria.getPai()) ? MateriaUtils.pathCategoria(categoria.getPai()) : "").concat(categoria.getUsarEmPrompts()
        ? "/".concat(Objects.nonNull(categoria.getApelido()) || !categoria.getApelido().isBlank() ? categoria.getApelido()
            : MateriaUtils.limparTexto(categoria.getTitulo()))
        : "");
  }

  public static String removeUrls(final String text) {
    return MateriaUtils.PATTERN_URL.matcher(text).replaceAll(" ");
  }

  /**
   * Não permite a criação de uma instância nova.
   */
  private MateriaUtils() {
  }
}
