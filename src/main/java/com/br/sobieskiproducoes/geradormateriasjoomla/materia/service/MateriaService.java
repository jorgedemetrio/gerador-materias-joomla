/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.MessageChatGPTDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.ChatGPTService;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.MateriaJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvert;
import com.fasterxml.jackson.databind.ObjectMapper;

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

  private final MateriaConvert convert;

  private final ChatGPTService chatgpt;

  private final ChatGPTProperties chatGPTProperties;

  private ObjectMapper objectMapper;

  private MateriaJoomlaClient client;

  private final Pattern ENTER = Pattern.compile("\\n");
  private final Pattern ASPAS_DUPLAS = Pattern.compile("\\\"");
  private final Pattern ASPAS_SIMPLES = Pattern.compile("\\'");

  private PropostaMateriaDTO convetToPropostaMateriaDTO(final MessageChatGPTDTO valor) {
    return objectMapper.convertValue(ENTER.matcher(// REMOVE O ENTER FALSO
        ASPAS_DUPLAS.matcher(// REMOVE AS ASPAS FALSAS
            ASPAS_SIMPLES.matcher( // REMOVE AS ASPAS FALSAS
                valor.getContent()).replaceAll("'"))
            .replaceAll("\""))
        .replaceAll("\n"), PropostaMateriaDTO.class);
  }

  public String materiaJoomla(final Long id, final LocalDateTime publicar) {
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

  private PropostaMateriaDTO salvarPropostaMateria(final PropostaMateriaDTO in) {
    return convert.convert(materiaRepository.save(convert.convert(in)));
  }

  public List<PropostaMateriaDTO> sugerirMateria(final SugerirMateriaDTO request) {

    final List<MessageChatGPTDTO> itensRetornoGPT = chatgpt
        .pergunta(chatGPTProperties.getPerguntas().getPedirMateria().formatted(
            chatGPTProperties.getEspecialista().stream().collect(Collectors.joining(", ")),
            chatGPTProperties.getSite(),
            chatGPTProperties.getRedesSociais().stream().collect(Collectors.joining(", ")),
            request.getTermos().stream().collect(Collectors.joining(", ")), request.getTema()));

    final List<PropostaMateriaDTO> itens = itensRetornoGPT.stream().map(this::convetToPropostaMateriaDTO)
        .collect(Collectors.toList());

    if (nonNull(request.getPublicar())) {
      itens.forEach(n -> n.setPublicar(request.getPublicar()));
    }

    return itens.stream().map(this::salvarPropostaMateria).collect(Collectors.toList());
  }

}
