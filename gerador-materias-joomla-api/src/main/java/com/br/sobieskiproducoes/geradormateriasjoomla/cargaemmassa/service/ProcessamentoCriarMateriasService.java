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

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository.MapaPerguntaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.convert.PerguntasConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaPorMataService;
import com.br.sobieskiproducoes.geradormateriasjoomla.utils.MateriaUtils;

import jakarta.transaction.Transactional;
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
public class ProcessamentoCriarMateriasService {

  private final ConfiguracoesProperties properties;

  private final GerarMateriaPorMataService gerarMateriaService;
  private final MateriaRepository repository;
  private final MapaPerguntaRepository mapaPerguntaRepository;
  private final PerguntasConvert perguntasConvert;
  private Boolean erroInterno = false;

  /**
   * @param uuid
   * @return
   */
  private List<MapaPerguntaDTO> extrairMapPerguntas(final String uuid) {
    return new ArrayList<>(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid).stream().map(mapaPerguntaEntity -> {
      final var mapaPerguntaDTO = perguntasConvert.toMapaPerguntaDTO(mapaPerguntaEntity);
      mapaPerguntaDTO.setPerguntasAlternativas(new ArrayList<>());
      mapaPerguntaDTO.setTermos(new ArrayList<>());
      mapaPerguntaEntity.getPerguntasAlternativas().forEach(pergunta -> mapaPerguntaDTO.getPerguntasAlternativas().add(pergunta.getPergunta()));

      mapaPerguntaEntity.getTermos().forEach(pergunta -> mapaPerguntaDTO.getTermos().add(pergunta.getTermo()));

      return mapaPerguntaDTO;
    }).toList());

  }

  @Transactional
  public Boolean iniciarProcesso(final RequisicaoCaragMassaDTO request, final String uuid) {
    erroInterno = false;
    final var itens = extrairMapPerguntas(uuid);
    final var hoje = LocalDate.now();
    log.info("Inicio de processamento lote em massa.");
    new File(properties.getCargaDadosImagens().getImagens().getPastaImagemMaterias()).mkdirs();

    LocalDateTime data = null;
    final Set<String> dataPublicadas = new HashSet<>();

    // Primeiro processa os com data válidas
    final var processarComData = itens.stream().filter(mapaPerguntaDTO -> {
      if (isNull(mapaPerguntaDTO.getDataSugestaoPublicacao())) {
        return false;
      }
      if (mapaPerguntaDTO.getDataSugestaoPublicacao().getYear() < hoje.getYear()
          || mapaPerguntaDTO.getDataSugestaoPublicacao().getYear() > hoje.getYear() + 1) {
        mapaPerguntaDTO.setDataSugestaoPublicacao(
            mapaPerguntaDTO.getDataSugestaoPublicacao().plusYears(hoje.getYear() - mapaPerguntaDTO.getDataSugestaoPublicacao().getYear()));
      }
      // Se a data proposta for maior ou igual a hoje e esteja dentro do range de
      // inicio e fim, se não será ignorada.
      return (mapaPerguntaDTO.getDataSugestaoPublicacao().isAfter(hoje) || mapaPerguntaDTO.getDataSugestaoPublicacao().isEqual(hoje))
          && ((mapaPerguntaDTO.getDataSugestaoPublicacao().isAfter(request.getDataInicioPublicacao())
              || mapaPerguntaDTO.getDataSugestaoPublicacao().isEqual(request.getDataInicioPublicacao()))

              && mapaPerguntaDTO.getDataSugestaoPublicacao().isBefore(request.getDataFimPublicacao())
              || mapaPerguntaDTO.getDataSugestaoPublicacao().isEqual(request.getDataFimPublicacao()));

    }).toList();
    for (final MapaPerguntaDTO mapaPerguntaDTO : processarComData) {

      data = MateriaUtils.getLocalDateTime(mapaPerguntaDTO.getDataSugestaoPublicacao(), request.getHorario());

      processarMateria(data, dataPublicadas, mapaPerguntaDTO, request, uuid);
      itens.remove(mapaPerguntaDTO);
    }

    var publicar = request.getDataInicioPublicacao();
    final var ultimaPublicacao = repository.ultimaData(uuid, request.getHorario());
    if (nonNull(ultimaPublicacao)) {
      publicar = ultimaPublicacao.toLocalDate().plus(1, java.time.temporal.ChronoUnit.DAYS);
    }

    // Será processado os que não tem data ou devem ignorar a data.

    final var rodar = nonNull(request.getDataFimPublicacao()) ? MateriaUtils.getDaysBetween(request.getDataInicioPublicacao(), request.getDataFimPublicacao())
        : 1L;
    var rodaram = 0L;
    while (rodaram <= rodar && itens.size() > 0) {
      final var mapaPerguntaDTO = itens.get(0);
      data = MateriaUtils.getLocalDateTime(publicar, request.getHorario(), rodaram);
      processarMateria(data, dataPublicadas, mapaPerguntaDTO, request, uuid);
      // Força remover
      rodaram++;
      itens.remove(0);
    }

    log.info("Fim de processamento lote em massa total processamento: " + dataPublicadas.size());
    dataPublicadas.forEach(n -> log.info("Materia publicada para ".concat(n)));
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
  private void processarMateria(final LocalDateTime data, final Set<String> dataPublicadas, final MapaPerguntaDTO mapaPerguntaDTO,
      final RequisicaoCaragMassaDTO request, final String uuid) {
    if (!dataPublicadas.contains(data.format(MateriaConstants.DATETIME_FORMATTER))) {
      try {
        final List<String> perguntas = perguntas(mapaPerguntaDTO);
        final Optional<MateriaEntity> item = repository.buscarPorPergunta(mapaPerguntaDTO.getId());

        if (item.isPresent()) {
          final MateriaEntity itemMateria = item.get();
          itemMateria.setPublicar(data);
          itemMateria.setCriadoJoomla(data);
          repository.save(itemMateria);
        } else {
          gerarMateriaService.gerarSugestaoMateria(new SugerirMateriaDTO(perguntas.stream().collect(Collectors.joining(", ")), mapaPerguntaDTO.getTermos(),
              data, mapaPerguntaDTO.getCategoria().getId(), request.getIdeias().getAudiencias()), uuid, mapaPerguntaDTO.getId());
        }

        dataPublicadas.add(data.format(MateriaConstants.DATETIME_FORMATTER));
      } catch (final Exception ex) {
        log.log(Level.SEVERE, "Erro o gravar a materia e publicar.", ex);
        erroInterno = true;
      }
    }
  }

}
