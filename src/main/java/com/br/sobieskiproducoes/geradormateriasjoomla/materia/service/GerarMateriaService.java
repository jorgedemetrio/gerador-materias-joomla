/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.ChoicesDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.model.LogDialogoChatGPTEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.repository.LogDialogoChatGPTRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.ChatGPTService;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.CategoriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
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

  private final MateriaConvert convert;

  private final ChatGPTService chatgpt;

  private final ChatGPTProperties chatGPTProperties;

  private final ObjectMapper objectMapper;

  private final LogDialogoChatGPTRepository logDialogoChatGPTRepository;

  private final Pattern ENTER = Pattern.compile("\\n");
  private final Pattern ASPAS_DUPLAS = Pattern.compile("\\\"");
  private final Pattern ASPAS_SIMPLES = Pattern.compile("\\'");

  private PropostaMateriaDTO convetToPropostaMateriaDTO(final ChoicesDTO choice) {

    try {
      final String content = limparTexto(choice.getMessage().getContent());
      return objectMapper.readValue(content, PropostaMateriaDTO.class);
    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao converter objeto de retorno do ChatGPT: ".concat(e.getMessage())
          .concat(" \nConteúdo: \n\n\n").concat(choice.getMessage().getContent()), e);
      throw new RuntimeException("Erro ao tentar processar os dados do ChatGPT", e);
    }

  }

  private void gravarLog(final RepostaResponseDTO itensDaMateriaRetornoGPT, final String uuid,
      final LocalDateTime inicio, final String pergunta) {
    try { // Grava o log da consulta.
      final List<LogDialogoChatGPTEntity> logs = itensDaMateriaRetornoGPT.getChoices().stream()
          .map(choice -> convert.convert(itensDaMateriaRetornoGPT, choice, inicio, pergunta, uuid))
          .collect(Collectors.toList());
      logs.forEach(logDialogoChatGPTRepository::save);
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Falha ao logar mensagens do ChatGPT no banco, mensagem:".concat(pergunta)
          .concat(". Erro: ").concat(ex.getMessage()), ex);
    }

  }

  private String limparTexto(final String in) throws IOException {
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

  private PropostaMateriaDTO salvarPropostaMateria(final PropostaMateriaDTO in, final SugerirMateriaDTO request) {
    try {
      final MateriaEntity materia = convert.convert(in);
      if (nonNull(request.getPublicar())) {
        materia.setPublicar(request.getPublicar());
      }
      materia.setTema(request.getTema());
      if (nonNull(request.getCategoria())) {
        final Optional<CategoriaEntity> categoria = categoriaRepository.findByIdJoomla(request.getCategoria());
        if (categoria.isPresent()) {
          materia.setCategoria(categoria.get());
        }
      }
      return convert.convert(materiaRepository.save(materia));
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Erro ao gravar a matéria: ".concat(request.getTema()), ex);
      log.info("Error ao gravar objeto: \n\n".concat(in.toString()));
      throw ex;
    }
  }

  /**
   * Gera materia conforme as informações fornecidas.
   *
   * @param request Parametrizaçãao das informações.
   * @return Lista de {@link PropostaMateriaDTO} com propostas de matéria.
   */
  @Transactional
  public List<PropostaMateriaDTO> sugerirMateria(@Validated final SugerirMateriaDTO request) {
    final String uuid = UUID.randomUUID().toString();
    final LocalDateTime inicio = LocalDateTime.now();

    final String redesSociais = chatGPTProperties.getRedesSociais().stream().collect(Collectors.joining(", "));
    final String conhecimento = chatGPTProperties.getEspecialista().stream().collect(Collectors.joining(", "));
    final String termos = request.getTermos().stream().collect(Collectors.joining(", "));

    final String perguntaDadosMateria = chatGPTProperties.getPerguntas().getPedirDadosMateria().formatted(conhecimento,
        chatGPTProperties.getSite(), redesSociais, termos, request.getTema());

    log.info("Perunta para o ChatGPT: \n\n".concat(perguntaDadosMateria));

    final String perguntaMateria = chatGPTProperties.getPerguntas().getPedirMateria().formatted(conhecimento,
        chatGPTProperties.getSite(), redesSociais, termos, request.getTema());

    log.info("Perunta para o ChatGPT: \n\n".concat(perguntaMateria));

    final RepostaResponseDTO itensDaMateriaRetornoGPT = chatgpt.pergunta(perguntaDadosMateria);

    gravarLog(itensDaMateriaRetornoGPT, uuid, inicio, perguntaDadosMateria);

    final List<PropostaMateriaDTO> propostasSemMateria = itensDaMateriaRetornoGPT.getChoices().stream()
        .map(this::convetToPropostaMateriaDTO).collect(Collectors.toList());

    final RepostaResponseDTO materiaRetornoGPT = chatgpt.pergunta(perguntaMateria);

    gravarLog(materiaRetornoGPT, uuid, inicio, perguntaMateria);

    final List<PropostaMateriaDTO> propostasRetorno = new ArrayList<>();
    try {
      PropostaMateriaDTO itemSalvar;
      for (final PropostaMateriaDTO item : propostasSemMateria) {
        for (final ChoicesDTO choice : materiaRetornoGPT.getChoices()) {
          if ((nonNull(choice.getMessage()) && nonNull(choice.getMessage().getContent())
              && !choice.getMessage().getContent().isBlank())
              && nonNull(itemSalvar = convert.copy(item, uuid, limparTexto(choice.getMessage().getContent())))) {
            propostasRetorno.add(this.salvarPropostaMateria(itemSalvar, request));
          }
        }
      }
    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao gravar a Matéria".concat(e.getMessage()), e);
    }
    return propostasRetorno;
  }

}
