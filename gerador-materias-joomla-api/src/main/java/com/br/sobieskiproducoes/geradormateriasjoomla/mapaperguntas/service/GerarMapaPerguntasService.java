/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service;

import static com.br.sobieskiproducoes.geradormateriasjoomla.utils.MateriaUtils.limparTextoJson;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.ChatGPTService;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.RequisitaPerguntasDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.MapaPerguntaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.SubMapaPerguntasEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.TermosMapaPerguntaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository.MapaPerguntaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.convert.PerguntasConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.dto.MapaPerguntaRetornoChatGPTDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.dto.QuestionsDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.CategoriaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 12:40:40
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class GerarMapaPerguntasService {

  private static final int PROCESSOS_POR_VEZ = 15;

  private final CategoriaRepository categoriaRepository;

  private final ChatGPTService chatgpt;

  private final ChatGPTProperties chatGPTProperties;

  private final ObjectMapper objectMapper;

  private final PerguntasConvert convert;

  private final MapaPerguntaRepository repository;

  private MapaPerguntaDTO convert(final MapaPerguntaEntity item) {
    if (isNull(item)) {
      return null;
    }

    final MapaPerguntaDTO retirno = convert.convert(item);

    retirno.setPerguntasAlternativas(item.getPerguntasAlternativas().stream().map(SubMapaPerguntasEntity::getPergunta).toList());

    return retirno;
  }

  private String converterCategoria(final CategoriaEntity categoria) {
    if (isNull(categoria)) {
      return null;
    }

    return "{\"id\":" + categoria.getId().toString() + ", \"nome\":\"" + categoria.getTitulo().trim().toLowerCase() + "\"}";

  }

  private List<MapaPerguntaEntity> convetToPropostaMateriaDTO(final String mensagem, final String uuid) {
    final List<MapaPerguntaEntity> itensRetorno = new ArrayList<>();
    try {
      String content = limparTextoJson(mensagem).trim();
      if (!content.trim().startsWith("[")) {
        content = "[" + content;
      }
      if (!content.trim().endsWith("]")) {
        content = content + "]";
      }
      List<MapaPerguntaRetornoChatGPTDTO> itens = null;

      if (content.contains("\"questions\":")) {
        List<QuestionsDTO> itensTMP = objectMapper.readValue(content, new TypeReference<List<QuestionsDTO>>() {
        });
        itens = new ArrayList<>();
        for (QuestionsDTO questionsDTO : itensTMP) {
          itens.addAll(questionsDTO.getQuestions());
        }
      } else {
        itens = objectMapper.readValue(content, new TypeReference<List<MapaPerguntaRetornoChatGPTDTO>>() {
        });

      }
      itensRetorno.addAll(itens.stream().map(mapaPergunta -> {
        if (isNull(mapaPergunta.getIdCategoria())) {
          return null;
        }
        final MapaPerguntaEntity entity = convert.convert(mapaPergunta);
        entity.setUuid(uuid);

        final Optional<CategoriaEntity> categoria = categoriaRepository.findById(mapaPergunta.getIdCategoria());
        if (categoria.isPresent()) {
          entity.setCategoria(categoria.get());
        }
        entity.setPerguntasAlternativas(
            mapaPergunta.getPerguntasAlternativas().stream().map(pergunta -> new SubMapaPerguntasEntity(null, uuid, pergunta, entity)).toList());

        entity.setTermos(mapaPergunta.getTermos().stream().map(termo -> new TermosMapaPerguntaEntity(null, uuid, termo, entity)).toList());

        return entity;
      }).filter(Objects::nonNull).toList());

    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao converter objeto de retorno do ChatGPT: ".concat(e.getMessage()).concat(" \nConteúdo: \n\n\n").concat(mensagem), e);
      // throw new RuntimeException("Erro ao tentar processar os dados do ChatGPT",
      // e);
    }
    return itensRetorno;
  }

  @Transactional
  public List<MapaPerguntaDTO> gerarMapa(final RequisitaPerguntasDTO request) throws Exception {
    return gerarMapa(request, UUID.randomUUID().toString());
  }

  @Transactional
  public List<MapaPerguntaDTO> gerarMapa(final RequisitaPerguntasDTO request, final String uuid) throws Exception {

    int processar = 0;

    // Prepara a pegunta

    String perguntaParaGerarPerguntas;

    List<String> repostaMapaPerguntas = null;

    long contador = 0;

    final LocalDateTime inicio = LocalDateTime.now();
    final List<MapaPerguntaEntity> itens = new ArrayList<>();
//    final List<MessageChatGPTDTO> perguntasChatGPTDTOs = new ArrayList<>();

    String mes = nonNull(request.getMes()) ? request.getMes().toString()
        : LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR"));

    String conhecimento = String.join(", ", chatGPTProperties.getEspecialista()).trim();
    String termos = String.join(", ", request.getTermos()).trim().toLowerCase();

    List<CategoriaEntity> categorias = nonNull(request.getCategorias()) && !request.getCategorias().isEmpty()
        ? categoriaRepository.findAllById(request.getCategorias())
        : categoriaRepository.categoriasParaPrompt();

    String categoriasJson = "[" + categorias.stream().filter(Objects::nonNull).map(this::converterCategoria).collect(Collectors.joining(", ")) + "]";

    String audiencias = nonNull(request.getAudiencias()) && !request.getAudiencias().isEmpty()
        ? request.getAudiencias().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "))
        : chatGPTProperties.getAudiencias().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));

    while (itens.size() < request.getQuantidade()) {

      // Prepara a massa de dados usada nas perguntas.

      if (itens.size() + PROCESSOS_POR_VEZ <= request.getQuantidade()) {
        processar = PROCESSOS_POR_VEZ;
        log.info("Quebrou o processamento do mapa restando processar : " + processar);
      } else {
        processar = request.getQuantidade() - itens.size();
        log.info("O total " + processar);
      }

      // Prepara a pegunta
      perguntaParaGerarPerguntas = chatGPTProperties.getPrompts().getPedirPerguntas().formatted(chatGPTProperties.getSite(), categoriasJson, conhecimento,
          audiencias, Integer.valueOf(processar), mes, contador == 0 ? "" : chatGPTProperties.getPrompts().getPedirDadosMateriaSeguinte(), termos);

      repostaMapaPerguntas = chatgpt.perguntarAssistente(perguntaParaGerarPerguntas, uuid, inicio);

      if (nonNull(repostaMapaPerguntas)) {

        repostaMapaPerguntas.forEach(mensagem -> {
          log.info("Mensagens:\n\n" + mensagem);
        });

        repostaMapaPerguntas.forEach(mensagem -> {
          itens.addAll(convetToPropostaMateriaDTO(mensagem, uuid));
        });

        contador++;
      }
    }
    log.info("Gravando mapa mental em lote! " + itens.size());
    repository.saveAll(itens);

    return itens.stream().map(this::convert).toList();

  }

}
