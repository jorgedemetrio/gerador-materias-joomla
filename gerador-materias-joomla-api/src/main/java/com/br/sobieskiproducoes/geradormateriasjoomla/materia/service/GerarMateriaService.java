/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static com.br.sobieskiproducoes.geradormateriasjoomla.utils.MateriaUtils.limparTexto;
import static com.br.sobieskiproducoes.geradormateriasjoomla.utils.MateriaUtils.limparTextoJson;
import static com.br.sobieskiproducoes.geradormateriasjoomla.utils.MateriaUtils.removeUrls;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.aspose.html.converters.Converter;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.ChatGPTService;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeVideoDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository.MapaPerguntaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.CategoriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.TagRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvert;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:06
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class GerarMateriaService {

  private final MateriaRepository materiaRepository;

  private final CategoriaRepository categoriaRepository;

  private final MapaPerguntaRepository mapaPerguntaRepository;

  private final TagRepository tagRepository;

  private final MateriaConvert convert;

  private final ChatGPTService chatgptService;

  private final ChatGPTProperties chatGPTProperties;

  private final ObjectMapper objectMapper;

  private static final String NULL = "null";

  private static final Pattern ENTER = Pattern.compile("[\n\r]");
  private static final Pattern TAB = Pattern.compile("\t");

  private PropostaMateriaDTO convetToPropostaMateriaDTO(final String mensagem) {

    try {
      final String content = limparTextoJson(TAB.matcher(ENTER.matcher(mensagem).replaceAll("")).replaceAll(" "));
      return objectMapper.readValue(content, PropostaMateriaDTO.class);
    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao converter objeto de retorno do ChatGPT: ".concat(e.getMessage()).concat(" \nConteúdo: \n\n\n").concat(mensagem), e);
    }
    return null;
  }

  @Transactional
  public List<PropostaMateriaDTO> gerarSugestaoMateria(@Validated final SugerirMateriaDTO request) throws Exception {
    return gerarSugestaoMateria(request, UUID.randomUUID().toString(), null);
  }

  /**
   * Gera materia conforme as informações fornecidas.
   *
   * @param request Parametrizaçãao das informações.
   * @return Lista de {@link PropostaMateriaDTO} com propostas de matéria.
   * @throws Exception
   */
  @Transactional
  public List<PropostaMateriaDTO> gerarSugestaoMateria(@Validated final SugerirMateriaDTO request, final String uuid, final Long idMapaProcessamento)
      throws Exception {
    /**************************************
     **************************************
     *
     * Preparando ambiente
     * 
     **************************************
     **************************************
     */
    if (isNull(request) || isNull(request.getTermos())) {
      return null;
    }

    final LocalDateTime inicio = LocalDateTime.now();

    final String redesSociais = chatGPTProperties.getRedesSociais().stream()
        .filter(n -> Objects.nonNull(n) && !n.isBlank() && !NULL.equalsIgnoreCase(n.trim())).map(n -> n.trim().toLowerCase())
        .collect(Collectors.joining(", "));
    final String conhecimento = chatGPTProperties.getEspecialista().stream()
        .filter(n -> Objects.nonNull(n) && !n.isBlank() && !NULL.equalsIgnoreCase(n.trim())).map(n -> n.trim().toLowerCase())
        .collect(Collectors.joining(", "));
    // Pegar interesses
    final String termos = request.getTermos().stream().filter(n -> Objects.nonNull(n) && !n.isBlank() && !NULL.equalsIgnoreCase(n.trim()))
        .map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));

    final String audiencias = nonNull(request.getAudiencias()) && !request.getAudiencias().isEmpty()
        ? request.getAudiencias().stream().filter(n -> Objects.nonNull(n) && !n.isBlank() && !NULL.equalsIgnoreCase(n.trim()))
            .map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "))
        : chatGPTProperties.getAudiencias().stream().filter(n -> Objects.nonNull(n) && !n.isBlank() && !NULL.equalsIgnoreCase(n.trim()))
            .map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));

    final String perguntaDadosMateria = chatGPTProperties.getPrompts().getPedirDadosMateria().formatted(conhecimento, chatGPTProperties.getSite(), redesSociais,
        audiencias, termos, request.getTema());

    /**************************************
     **************************************
     *
     * Executa pergunta dados
     * 
     **************************************
     **************************************
     */

    final List<String> itensDaMateriaRetornoGPT = chatgptService.perguntarAssistente(perguntaDadosMateria, uuid, inicio);

    final List<PropostaMateriaDTO> propostasSemMateria = itensDaMateriaRetornoGPT.stream().map(this::convetToPropostaMateriaDTO).filter(n -> Objects.nonNull(n))
        .toList();

    PropostaMateriaDTO propostaMateriaDTO = nonNull(propostasSemMateria) && !propostasSemMateria.isEmpty()
        ? propostasSemMateria.get(propostasSemMateria.size() - 1)
        : null;

    if (isNull(propostaMateriaDTO)) {
      log.info("Não conseguiu gerar a proposta de mateiria.");
      return null;
    }

    final List<String> titulosArry = new ArrayList<>(propostaMateriaDTO.getTitulos());
    titulosArry.add(propostaMateriaDTO.getTema());

    final String titulos = titulosArry.stream().filter(n -> Objects.nonNull(n) && !n.isBlank() && !NULL.equalsIgnoreCase(n.trim()))
        .collect(Collectors.joining(", \n"));

    /**************************************
     **************************************
     *
     * Executa pergunta matéria
     * 
     **************************************
     **************************************
     */

    final String perguntaMateria = chatGPTProperties.getPrompts().getPedirMateria().formatted(conhecimento, chatGPTProperties.getSite(), redesSociais,
        audiencias, termos, titulos);

    String materia = null;
    final List<PropostaMateriaDTO> propostasRetorno = new ArrayList<>();
    try {
      materia = gerarTextoMateria(perguntaMateria, uuid, inicio, 0);
      PropostaMateriaDTO itemSalvar = propostaMateriaDTO;
      itemSalvar = convert.copy(itemSalvar, uuid, materia);
      if (nonNull(itemSalvar)) {
        propostasRetorno.add(this.salvarPropostaMateria(itemSalvar, request, idMapaProcessamento));
      }

    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao gravar a Matéria".concat(e.getMessage()), e);
    }
    return propostasRetorno;
  }

  /**
   * Sistema de tentativas de gerar a materia
   * 
   * @param uuid
   * @param inicio
   * @param perguntaMateria
   * @param tentativas      Número de tentativas
   * @return
   * @throws IOException
   */
  private String gerarTextoMateria(final String perguntaMateria, final String uuid, final LocalDateTime inicio, int tentativas) throws Exception {
    String materia = null;
    log.info("Gerando texto materia tentativa: " + tentativas);
    final List<String> materiaRetornoGPT = chatgptService.perguntarAssistente(perguntaMateria, uuid, inicio);

    if (nonNull(materiaRetornoGPT) && !materiaRetornoGPT.isEmpty()) {
      // Pega o ultimo registro da array.
      materia = textoMateria(materiaRetornoGPT.get(materiaRetornoGPT.size() - 1));
    }

    if (nonNull(materia) && !materia.isBlank() && materia.contains("#")) {
      materia = convertMarkdownToHtml(materia);
    }
    if (tentativas >= 3) {
      log.info(
          "Motivo de não gerar a matéria: " + chatgptService.perguntarAssistente("Porque não pode gerar o texto que pedi?", uuid, inicio).get(0) + "\n\n\n");
    }
    // Se materia for null ele tenta 3 vezes

    if (nonNull(materia)) {
      return materia;
    }
    if (tentativas < 3) {
      return gerarTextoMateria(perguntaMateria, uuid, inicio, ++tentativas);
    }

    return null;
  }

  private String convertMarkdownToHtml(String markdown) {
    // Convert Markdown string to InputStream
    InputStream inputStream = new ByteArrayInputStream(markdown.getBytes(StandardCharsets.UTF_8));

    return Converter.convertMarkdown(inputStream, "text/html").getTextContent();
  }

  @Transactional
  public List<PropostaMateriaDTO> gerarSugestaoMateria(@Validated final YoutubeVideoDTO request) throws Exception {
    return gerarSugestaoMateria(request, UUID.randomUUID().toString(), null);
  }

  /**
   * Gera materia conforme as informações fornecidas.
   *
   * @param request Parametrizaçãao das informações.
   * @return Lista de {@link PropostaMateriaDTO} com propostas de matéria.
   * @throws Exception
   */
  @Transactional
  public List<PropostaMateriaDTO> gerarSugestaoMateria(@Validated final YoutubeVideoDTO request, final String uuid, final Long idMapaProcessamento)
      throws Exception {

    final var inicio = LocalDateTime.now();
    final var redesSociais = chatGPTProperties.getRedesSociais().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));
    final var conhecimento = chatGPTProperties.getEspecialista().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));
    final var termos = request.getTitulo().concat(", ") + request.getTags().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));
    final var audiencias = chatGPTProperties.getAudiencias().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));
    final var tema = "Titulo " + request.getTitulo()
        + (nonNull(request.getDescricao()) && !request.getDescricao().isBlank() ? "\n sobre :" + removeUrls(request.getDescricao()) : "")
        + (nonNull(request.getLegenda()) && !request.getLegenda().isBlank()
            ? "\n Use essa legenda como base de informação para matéria :" + removeUrls(request.getLegenda())
            : "");
    final var perguntaDadosMateria = chatGPTProperties.getPrompts().getPedirDadosMateria().formatted(conhecimento, chatGPTProperties.getSite(), redesSociais,
        audiencias, termos, tema);
    final List<String> itensDaMateriaRetornoGPT = chatgptService.perguntarAssistente(perguntaDadosMateria, uuid, inicio);
    final List<PropostaMateriaDTO> propostasSemMateria = itensDaMateriaRetornoGPT.stream().map(this::convetToPropostaMateriaDTO).toList();
    final List<String> titulosArry = new ArrayList<>();
    propostasSemMateria.forEach(n -> {
      titulosArry.addAll(n.getTitulos());
      titulosArry.add(n.getTema());
    });
    final var titulos = titulosArry.stream().filter(n -> Objects.nonNull(n) && !n.isBlank()).collect(Collectors.joining(", \n"));
    final var perguntaMateria = chatGPTProperties.getPrompts().getPedirMateria().formatted(conhecimento, chatGPTProperties.getSite(), redesSociais, audiencias,
        termos, titulos);

    final List<PropostaMateriaDTO> propostasRetorno = new ArrayList<>();

    try {
      final List<String> materiaRetornoGPT = chatgptService.perguntarAssistente(perguntaMateria, uuid, inicio);
      PropostaMateriaDTO itemSalvar;
      for (final PropostaMateriaDTO item : propostasSemMateria) {
        for (final String mensagem : materiaRetornoGPT) {
          if (nonNull(mensagem) && !mensagem.isBlank()) {
            itemSalvar = convert.copy(item, uuid, limparTexto(mensagem));
            if (nonNull(itemSalvar)) {
              propostasRetorno.add(this.salvarPropostaMateria(itemSalvar, request, idMapaProcessamento));
            }
          }
        }
      }
    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao gravar a Matéria".concat(e.getMessage()), e);
    }
    return propostasRetorno;
  }

  private PropostaMateriaDTO salvarPropostaMateria(final PropostaMateriaDTO in, final SugerirMateriaDTO request, final Long idMapaProcessamento) {
    try {
      final var materia = convert.convert(in);
      if (nonNull(request.getPublicar())) {
        materia.setPublicar(request.getPublicar());
      }

      materia.setTema(request.getTema());
      materia.getFaqs().forEach(n -> n.setUuid(in.getUuid()));
      // Verifica se a Tags já existe, se não da um uuid da sua crição.
      materia.setTags(materia.getTags().stream().map(n -> {
        final var entities = tagRepository.buscarPorApelidoTitulo(n.getApelido(), n.getTitulo());
        if (entities.isPresent()) {
          return entities.get();
        }
        n.setUuid(in.getUuid());
        return n;
      }).toList());

      if (nonNull(request.getCategoria())) {
        final var categoria = categoriaRepository.findById(request.getCategoria());
        if (categoria.isPresent()) {
          materia.setCategoria(categoria.get());
        }
      }

      materia.setStatus(StatusProcessamentoEnum.PROCESSAR);
      if (isNull(in.getMateria())) {
        materia.setStatus(StatusProcessamentoEnum.ERRO);
      }

      if (nonNull(idMapaProcessamento)) {
        materia.setPeguntaPrincipal(mapaPerguntaRepository.findById(idMapaProcessamento).get());
      }
      return convert.convert(materiaRepository.save(materia));
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Erro ao gravar a matéria: ".concat(request.getTema()), ex);
      log.info("Error ao gravar objeto: \n\n".concat(in.toString()));
      throw ex;
    }
  }

  private PropostaMateriaDTO salvarPropostaMateria(final PropostaMateriaDTO in, final YoutubeVideoDTO request, final Long idMapaProcessamento) {
    try {
      final var materia = convert.convert(in);

      materia.setTema(request.getTitulo());
      materia.getFaqs().forEach(n -> n.setUuid(in.getUuid()));
      // Verifica se a Tags já existe, se não da um uuid da sua crição.
      materia.setTags(materia.getTags().stream().map(n -> {
        final var entities = tagRepository.buscarPorApelidoTitulo(n.getApelido(), n.getTitulo());
        if (entities.isPresent()) {
          return entities.get();
        }
        n.setUuid(in.getUuid());
        return n;
      }).toList());

      materia.setStatus(StatusProcessamentoEnum.PROCESSAR);
      if (nonNull(idMapaProcessamento)) {
        materia.setPeguntaPrincipal(mapaPerguntaRepository.findById(idMapaProcessamento).get());
      }
      return convert.convert(materiaRepository.save(materia));
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Erro ao gravar a matéria: ".concat(request.getTitulo()), ex);
      log.info("Error ao gravar objeto: \n\n".concat(in.toString()));
      throw ex;
    }
  }

  private String textoMateria(final String materia) throws IOException {
    final var texto = limparTexto(materia);
    for (final String item : chatGPTProperties.getFalhas()) {
      if (texto.trim().toLowerCase().startsWith(item.toLowerCase().trim())) {
        log.info("Corpo inválido de materia : " + texto.trim());

        return null;
      }

    }
    return texto;
  }

}
