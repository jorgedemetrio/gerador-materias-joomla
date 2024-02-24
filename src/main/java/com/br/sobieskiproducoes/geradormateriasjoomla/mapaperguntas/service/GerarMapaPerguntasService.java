/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service;

import static com.br.sobieskiproducoes.geradormateriasjoomla.utils.SugerirMateriaUtils.limparTexto;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.ChoicesDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.ChatGPTService;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.RequisitaPerguntasDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.MapaPerguntaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.SubMapaPerguntasEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository.MapaPerguntaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.convert.PerguntasConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.CategoriaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

  private final CategoriaRepository categoriaRepository;

  private final ChatGPTService chatgpt;

  private final ChatGPTProperties chatGPTProperties;

  private final ObjectMapper objectMapper;

  private final PerguntasConvert convert;

  private final MapaPerguntaRepository repository;

  private MapaPerguntaDTO convert(final MapaPerguntaEntity item) {
    if(isNull(item)) {
      return null;
    }

    final MapaPerguntaDTO retirno = convert.convert(item);

    retirno.setPerguntasAlternativas(
        item.getPerguntasAlternativas().stream().map(SubMapaPerguntasEntity::getPergunta).collect(Collectors.toList()));

    return retirno;
  }

  private List<MapaPerguntaEntity> convetToPropostaMateriaDTO(final ChoicesDTO choice) {

    try {
      final String content = limparTexto(choice.getMessage().getContent());
      return objectMapper.readValue(content, new TypeReference<List<MapaPerguntaEntity>>() {
      });
    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao converter objeto de retorno do ChatGPT: ".concat(e.getMessage())
          .concat(" \nConteúdo: \n\n\n").concat(choice.getMessage().getContent()), e);
      throw new RuntimeException("Erro ao tentar processar os dados do ChatGPT", e);
    }

  }

  public List<MapaPerguntaDTO> gerarMapa(final RequisitaPerguntasDTO request) {

    final String uuid = UUID.randomUUID().toString();
    final LocalDateTime inicio = LocalDateTime.now();

    final String mes = nonNull(request.getMes()) || !request.getMes().isBlank() ? request.getMes()
        : LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-BR"));

    final String conhecimento = chatGPTProperties.getEspecialista().stream().map(n -> n.trim().toLowerCase())
        .collect(Collectors.joining(", "));
    final String termos = request.getTermos().stream().map(n -> n.trim().toLowerCase())
        .collect(Collectors.joining(", "));

    final String categorias = categoriaRepository.categoriasParaPrompt().stream()
        .map(n -> n.getTitulo().trim().toLowerCase()).collect(Collectors.joining(", "));

    final String audiencias = nonNull(request.getAudiencias()) && !request.getAudiencias().isEmpty()
        ? request.getAudiencias().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "))
        : chatGPTProperties.getAudiencias().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));

    final String perguntaParaGerarPerguntas = chatGPTProperties.getPrompts().getPedirPerguntas().formatted(
        chatGPTProperties.getSite(), categorias, conhecimento, audiencias, request.getQuantidade(), mes, termos);

    final RepostaResponseDTO repostaMapaPerguntas = chatgpt.pergunta(perguntaParaGerarPerguntas, uuid, inicio);

    final List<MapaPerguntaEntity> itens = new ArrayList<>();

    repostaMapaPerguntas.getChoices().forEach(choice -> {
      itens.addAll(convetToPropostaMateriaDTO(choice));
    });

    repository.saveAll(itens);

    return itens.stream().map(this::convert).collect(Collectors.toList());

  }

}
