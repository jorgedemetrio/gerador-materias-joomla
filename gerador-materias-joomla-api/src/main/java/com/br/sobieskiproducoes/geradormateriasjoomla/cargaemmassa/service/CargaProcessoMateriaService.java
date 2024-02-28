/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.HorarioRequisiscaoDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository.MapaPerguntaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.convert.PerguntasConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.BusinessException;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaJoomlaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.utils.SugerirMateriaUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 20:16:51
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Component
public class CargaProcessoMateriaService {

  private final ConfiguracoesProperties properties;
  private final MateriaJoomlaService materiaJoomlaService;
  private final GerarMateriaService gerarMateriaService;
  private final MateriaRepository repository;
  private final MapaPerguntaRepository mapaPerguntaRepository;
  private final PerguntasConvert perguntasConvert;

  private Boolean erroInterno = false;

  /**
   * @param uuid
   * @return
   */
  private List<MapaPerguntaDTO> extrairMapPerguntas(final String uuid) {
    return mapaPerguntaRepository.findByUuid(uuid).stream().map(mapaPerguntaEntity -> {
      final MapaPerguntaDTO mapaPerguntaDTO = perguntasConvert.toMapaPerguntaDTO(mapaPerguntaEntity);
      mapaPerguntaDTO.setPerguntasAlternativas(new ArrayList<>());
      mapaPerguntaDTO.setTermos(new ArrayList<>());
      mapaPerguntaEntity.getPerguntasAlternativas()
          .forEach(pergunta -> mapaPerguntaDTO.getPerguntasAlternativas().add(pergunta.getPergunta()));

      mapaPerguntaEntity.getTermos().forEach(pergunta -> mapaPerguntaDTO.getTermos().add(pergunta.getTermo()));

      return mapaPerguntaDTO;
    }).collect(Collectors.toList());
  }

  @Transactional
  public Boolean iniciarProcesso(final RequisicaoCaragMassaDTO request, final String uuid) {
    erroInterno = false;
    final List<MapaPerguntaDTO> itens = extrairMapPerguntas(uuid);
    final LocalDate hoje = LocalDate.now();
    log.info("Inicio de processamento lote em massa.");
    new File(properties.getCargaDadosImagens().getImagens().getPastaImagemMaterias()).mkdirs();

    LocalDateTime data = null;
    final Set<String> dataPublicadas = new HashSet<>();
    for (final HorarioRequisiscaoDTO hora : request.getHoarios()) {

      // Primeiro processa os com data válidas
      final List<MapaPerguntaDTO> processarComData = itens.stream().filter(mapaPerguntaDTO -> {
        if (isNull(mapaPerguntaDTO.getDataSugestaoPublicacao())) {
          return false;
        }
        if (mapaPerguntaDTO.getDataSugestaoPublicacao().getYear() < hoje.getYear()
            || mapaPerguntaDTO.getDataSugestaoPublicacao().getYear() > hoje.getYear() + 1) {
          mapaPerguntaDTO.setDataSugestaoPublicacao(mapaPerguntaDTO.getDataSugestaoPublicacao()
              .plusYears(hoje.getYear() - mapaPerguntaDTO.getDataSugestaoPublicacao().getYear()));
        }
        // Se a data proposta for maior ou igual a hoje e esteja dentro do range de
        // inicio e fim, se não será ignorada.
        return (mapaPerguntaDTO.getDataSugestaoPublicacao().isAfter(hoje)
            || mapaPerguntaDTO.getDataSugestaoPublicacao().isEqual(hoje))
            && ((mapaPerguntaDTO.getDataSugestaoPublicacao().isAfter(request.getDataInicioPublicacao())
                || mapaPerguntaDTO.getDataSugestaoPublicacao().isEqual(request.getDataInicioPublicacao()))

                && mapaPerguntaDTO.getDataSugestaoPublicacao().isBefore(request.getDataFimPublicacao())
                || mapaPerguntaDTO.getDataSugestaoPublicacao().isEqual(request.getDataFimPublicacao()));

      }).collect(Collectors.toList());
      for (final MapaPerguntaDTO mapaPerguntaDTO : processarComData) {

        data = SugerirMateriaUtils.getLocalDateTime(mapaPerguntaDTO.getDataSugestaoPublicacao(), hora.getHoraio());

        processarMateria(itens, data, dataPublicadas, mapaPerguntaDTO, request, uuid);

      }
    }
    // Será processado os que não tem data ou devem ignorar a data.
    for (final HorarioRequisiscaoDTO hora : request.getHoarios()) {
      final long rodar = nonNull(request.getDataFimPublicacao())
          ? SugerirMateriaUtils.getDaysBetween(request.getDataInicioPublicacao(), request.getDataFimPublicacao())
          : 1L;
      long rodaram = 0;
      while (rodaram <= rodar && itens.size() > 0) {

        final MapaPerguntaDTO mapaPerguntaDTO = itens.get(0);
        data = SugerirMateriaUtils.getLocalDateTime(request.getDataInicioPublicacao(), hora.getHoraio(), rodaram);
        processarMateria(itens, data, dataPublicadas, mapaPerguntaDTO, request, uuid);
        // Força remover
        itens.remove(mapaPerguntaDTO);
        rodaram++;
      }
    }
    log.info("Fim de processamento lote em massa total processamento: " + dataPublicadas.size());
    dataPublicadas.forEach(n -> log.info("Materia publciada para ".concat(n)));
    return !erroInterno;
  }

  private List<String> perguntas(final MapaPerguntaDTO dto) {
    final List<String> itens = new ArrayList<>(dto.getPerguntasAlternativas());
    itens.add(dto.getPergunta());
    return itens;
  }

  /**
   * @param data
   * @param dataPublicadas
   * @param mapaPerguntaDTO
   */
  private void processarMateria(final List<MapaPerguntaDTO> itens, final LocalDateTime data,
      final Set<String> dataPublicadas, final MapaPerguntaDTO mapaPerguntaDTO, final RequisicaoCaragMassaDTO request,
      final String uuid) {
    if (!dataPublicadas.contains(data.format(MateriaConstants.DATETIME_FORMATTER))) {
      try {
        final List<String> perguntas = perguntas(mapaPerguntaDTO);

        List<PropostaMateriaDTO> maerias = null;

        final Optional<MateriaEntity> materia = repository.buscarPorPergunta(mapaPerguntaDTO.getId());

        if (materia.isPresent()) {
          processarMateriaNoJoomla(data, materia.get());
        } else {
          maerias = gerarMateriaService.gerarSugestaoMateria(
              new SugerirMateriaDTO(perguntas.stream().collect(Collectors.joining(", ")), mapaPerguntaDTO.getTermos(),
                  data, mapaPerguntaDTO.getCategoria().getId(), request.getIdeias().getAudiencias()),
              uuid, mapaPerguntaDTO.getId());
          if (request.getPublicar()) {
            for (final PropostaMateriaDTO propostaMateriaDTO : maerias) {
              final Optional<MateriaEntity> materiaEntity = repository.findById(propostaMateriaDTO.getId());
              if (materiaEntity.isPresent()) {
                processarMateriaNoJoomla(data, materiaEntity.get());
              }
            }
          }
        }

        dataPublicadas.add(data.format(MateriaConstants.DATETIME_FORMATTER));
        itens.remove(mapaPerguntaDTO);
      } catch (final Exception ex) {
        log.log(Level.SEVERE, "Erro o gravar a materia e publicar.", ex);
      }
    }
  }

  /**
   * @param data
   * @param propostaMateriaDTO
   * @param materia
   * @throws BusinessException
   */
  private void processarMateriaNoJoomla(final LocalDateTime data, final MateriaEntity materiaEntity)
      throws BusinessException {
    if (StatusProcessamentoEnum.PROCESSADO.equals(materiaEntity.getStatus())) {
      return;
    }
    try {
      new File(properties.getCargaDadosImagens().getImagens().getPastaImagemMaterias()
          .concat(SugerirMateriaUtils.pathCategoria(materiaEntity.getCategoria()))).mkdirs();
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Erro ao criar a pasta da materia : ".concat(materiaEntity.getId().toString()), ex);
    }
    if (isNull(materiaJoomlaService.publicarMateriaJoomla(materiaEntity, data))) {
      erroInterno = true;
    }

  }
}
