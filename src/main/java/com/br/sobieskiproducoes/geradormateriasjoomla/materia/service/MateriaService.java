/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.nonNull;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.MessageChatGPTDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.model.LogDialogoChatGPTEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.repository.LogDialogoChatGPTRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.ChatGPTService;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.MateriaJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaDTO;
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
public class MateriaService {

  private final MateriaRepository materiaRepository;

  private final CategoriaRepository categoriaRepository;

  private final MateriaConvert convert;

  private final ChatGPTService chatgpt;

  private final ChatGPTProperties chatGPTProperties;

  private final ObjectMapper objectMapper;

  private final MateriaJoomlaClient client;

  private final LogDialogoChatGPTRepository logDialogoChatGPTRepository;

  private final Pattern ENTER = Pattern.compile("\\n");
  private final Pattern ASPAS_DUPLAS = Pattern.compile("\\\"");
  private final Pattern ASPAS_SIMPLES = Pattern.compile("\\'");

  private PropostaMateriaDTO convetToPropostaMateriaDTO(final MessageChatGPTDTO valor) {
    try {

      return objectMapper.readValue(
          // Converte os caracteres especiais
          StreamUtils.copyToString(new ByteArrayInputStream(
              ENTER.matcher(// REMOVE O ENTER FALSO
                  ASPAS_DUPLAS.matcher(// REMOVE AS ASPAS FALSAS
                      ASPAS_SIMPLES.matcher( // REMOVE AS ASPAS FALSAS
                          valor.getContent()
                      ).replaceAll("'")).replaceAll("\"")).replaceAll("\n").getBytes()), StandardCharsets.UTF_8),
          PropostaMateriaDTO.class);
    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao converter objeto de retorno do ChatGPT: ".concat(e.getMessage())
          .concat(" \nConteúdo: \n\n\n").concat(valor.getContent()), e);
      throw new RuntimeException("Erro ao tentar processar os dados do ChatGPT", e);
    }
  }

  public String publicarMateriaJoomla(final Long id, final LocalDateTime publicar) {
    final MateriaEntity entity = materiaRepository.findById(id).get();
    if (nonNull(publicar)) {
      entity.setPublicar(publicar);
      materiaRepository.save(entity);
    }
    final AtributosArtigoJoomlaDTO item = convert.convertJoomla(entity);
    item.setTags(new HashMap<>());

    // Mapeando Tags
    entity.getTags().forEach(n -> item.getTags().put(n.getId().toString(), n.getTitulo()));

    return client.gravar(item);
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

  @Transactional
  public List<PropostaMateriaDTO> sugerirMateria(final SugerirMateriaDTO request) {
    final LocalDateTime inicio = LocalDateTime.now();
    final String pergunta = chatGPTProperties.getPerguntas().getPedirMateria().formatted(
        chatGPTProperties.getEspecialista().stream().collect(Collectors.joining(", ")), chatGPTProperties.getSite(),
        chatGPTProperties.getRedesSociais().stream().collect(Collectors.joining(", ")),
        request.getTermos().stream().collect(Collectors.joining(", ")), request.getTema());

    log.info("Perunta realziada para o ChatGPT: ".concat(pergunta));

    final List<MessageChatGPTDTO> itensRetornoGPT = chatgpt.pergunta(pergunta);

    try { // Grava o log da consulta.
      itensRetornoGPT.forEach(n -> logDialogoChatGPTRepository
          .save(new LogDialogoChatGPTEntity(null, LocalDateTime.now(), inicio, pergunta, n.getContent(), null)));
    } finally {
    }
    final List<PropostaMateriaDTO> itens = itensRetornoGPT.stream().map(

        this::convetToPropostaMateriaDTO

    ).collect(Collectors.toList());

    return itens.stream().map(n -> this.salvarPropostaMateria(n, request)).collect(Collectors.toList());
  }

}
