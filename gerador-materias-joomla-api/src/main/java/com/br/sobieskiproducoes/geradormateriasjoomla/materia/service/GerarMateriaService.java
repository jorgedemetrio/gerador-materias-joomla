/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static com.br.sobieskiproducoes.geradormateriasjoomla.utils.SugerirMateriaUtils.limparTexto;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.MessageChatGPTDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.ChoicesDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.ChatGPTService;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository.MapaPerguntaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.TagEntity;
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

  private final ChatGPTService chatgpt;

  private final ChatGPTProperties chatGPTProperties;
  private final ConfiguracoesProperties properties;

  private final ObjectMapper objectMapper;

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

  @Transactional
  public List<PropostaMateriaDTO> gerarSugestaoMateria(@Validated final SugerirMateriaDTO request) {
    return gerarSugestaoMateria(request, UUID.randomUUID().toString(), null);
  }

  /**
   * Gera materia conforme as informações fornecidas.
   *
   * @param request Parametrizaçãao das informações.
   * @return Lista de {@link PropostaMateriaDTO} com propostas de matéria.
   */
  @Transactional
  public List<PropostaMateriaDTO> gerarSugestaoMateria(@Validated final SugerirMateriaDTO request, final String uuid,
      final Long idMapaProcessamento) {

    if (isNull(request) || isNull(request.getTermos())) {
      return null;
    }

    final LocalDateTime inicio = LocalDateTime.now();

    final String redesSociais = chatGPTProperties.getRedesSociais().stream().map(n -> n.trim().toLowerCase())
        .collect(Collectors.joining(", "));
    final String conhecimento = chatGPTProperties.getEspecialista().stream().map(n -> n.trim().toLowerCase())
        .collect(Collectors.joining(", "));
    // Pegar interesses
    final String termos =

        request.getTermos().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));

    final String audiencias = nonNull(request.getAudiencias()) && !request.getAudiencias().isEmpty()
        ? request.getAudiencias().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "))
        : chatGPTProperties.getAudiencias().stream().map(n -> n.trim().toLowerCase()).collect(Collectors.joining(", "));

    final String perguntaDadosMateria = chatGPTProperties.getPrompts().getPedirDadosMateria().formatted(conhecimento,
        chatGPTProperties.getSite(), redesSociais, audiencias, termos, request.getTema());

    final RepostaResponseDTO itensDaMateriaRetornoGPT = chatgpt.pergunta(perguntaDadosMateria, uuid, inicio);

    final List<MessageChatGPTDTO> perguntasChatGPTDTOs = new ArrayList<>();
    perguntasChatGPTDTOs.add(new MessageChatGPTDTO(properties.getChatgpt().getRoleUser(), perguntaDadosMateria));
    perguntasChatGPTDTOs.add(new MessageChatGPTDTO(itensDaMateriaRetornoGPT.getChoices().get(0).getMessage().getRole(),
        itensDaMateriaRetornoGPT.getChoices().get(0).getMessage().getContent()));

    final List<PropostaMateriaDTO> propostasSemMateria = itensDaMateriaRetornoGPT.getChoices().stream()
        .map(this::convetToPropostaMateriaDTO).collect(Collectors.toList());

    final String perguntaMateria = chatGPTProperties.getPrompts().getPedirMateria().formatted(conhecimento,
        chatGPTProperties.getSite(), redesSociais, audiencias, termos, request.getTema());

    perguntasChatGPTDTOs.add(new MessageChatGPTDTO(properties.getChatgpt().getRoleUser(), perguntaMateria));

    final RepostaResponseDTO materiaRetornoGPT = chatgpt.perguntasObjeto(perguntasChatGPTDTOs, uuid, inicio);

    final List<PropostaMateriaDTO> propostasRetorno = new ArrayList<>();
    try {
      PropostaMateriaDTO itemSalvar;
      for (final PropostaMateriaDTO item : propostasSemMateria) {
        for (final ChoicesDTO choice : materiaRetornoGPT.getChoices()) {
          if ((nonNull(choice.getMessage()) && nonNull(choice.getMessage().getContent())
              && !choice.getMessage().getContent().isBlank())
              && nonNull(itemSalvar = convert.copy(item, uuid, limparTexto(choice.getMessage().getContent())))) {
            propostasRetorno.add(this.salvarPropostaMateria(itemSalvar, request, idMapaProcessamento));
          }
        }
      }
    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro ao gravar a Matéria".concat(e.getMessage()), e);
    }
    return propostasRetorno;
  }

  private PropostaMateriaDTO salvarPropostaMateria(final PropostaMateriaDTO in, final SugerirMateriaDTO request,
      final Long idMapaProcessamento) {
    try {
      final MateriaEntity materia = convert.convert(in);
      if (nonNull(request.getPublicar())) {
        materia.setPublicar(request.getPublicar());
      }
      materia.setTema(request.getTema());
      materia.getFaqs().forEach(n -> n.setUuid(in.getUuid()));
      // Verifica se a Tags já existe, se não da um uuid da sua crição.
      materia.setTags(materia.getTags().stream().map(n -> {
        final List<TagEntity> entities = tagRepository.findByTitulo(n.getTitulo().trim());
        if (entities.size() > 0) {
          return entities.get(0);
        }
        n.setUuid(in.getUuid());
        return n;
      }).collect(Collectors.toList()));

      if (nonNull(request.getCategoria())) {
        final Optional<CategoriaEntity> categoria = categoriaRepository.findByIdJoomla(request.getCategoria());
        if (categoria.isPresent()) {
          materia.setCategoria(categoria.get());
        }
      }
      materia.setStatus(StatusProcessamentoEnum.PROCESSAR);
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

}
