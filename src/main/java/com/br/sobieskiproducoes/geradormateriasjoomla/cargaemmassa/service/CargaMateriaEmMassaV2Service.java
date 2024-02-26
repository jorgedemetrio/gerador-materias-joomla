/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import static java.util.Objects.nonNull;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.HorarioRequisiscaoDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.GerarMapaPerguntasService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaJoomlaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.utils.SugerirMateriaUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 09:44:23
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class CargaMateriaEmMassaV2Service {

  private final GerarMapaPerguntasService gerarMapaPerguntasService;
  private final GerarMateriaService gerarMateriaService;
  private final MateriaJoomlaService materiaJoomlaService;
  private final ConfiguracoesProperties properties;

  private List<String> getTermos(final MapaPerguntaDTO dto) {
    final List<String> itens = new ArrayList<>(dto.getPerguntasAlternativas());
    itens.add(dto.getPergunta());
    return itens;
  }

  public List<MapaPerguntaDTO> processar(final RequisicaoCaragMassaDTO request) {

    log.info("Inicio processamento ".concat(LocalDateTime.now().toString()));
    final String uuid = UUID.randomUUID().toString();
    final List<MapaPerguntaDTO> itens = gerarMapaPerguntasService.gerarMapa(request.getIdeias(), uuid);

    processarMaterias(uuid, request, itens);

    log.info("Concluído processamento dos mapas e vai iniciar os da materias em lote"
        .concat(LocalDateTime.now().toString()));
    return itens;
  }

  /**
   * @param data
   * @param dataPublicadas
   * @param mapaPerguntaDTO
   */
  private void processarMateriaJoomla(final LocalDateTime data, final Set<String> dataPublicadas,
      final MapaPerguntaDTO mapaPerguntaDTO, final String uuid, final RequisicaoCaragMassaDTO request,
      final List<MapaPerguntaDTO> itens) {
    if (!dataPublicadas.contains(data.format(MateriaConstants.DATETIME_FORMATTER))) {
      try {
        final List<PropostaMateriaDTO> maerias = gerarMateriaService
            .gerarSugestaoMateria(new SugerirMateriaDTO(uuid, getTermos(mapaPerguntaDTO), data,
                mapaPerguntaDTO.getCategoria().getId(), request.getIdeias().getAudiencias()));
        if (request.getPublicar()) {
          for (final PropostaMateriaDTO propostaMateriaDTO : maerias) {
            materiaJoomlaService.publicarMateriaJoomla(propostaMateriaDTO.getId(), data);

          }
        }
        dataPublicadas.add(data.format(MateriaConstants.DATETIME_FORMATTER));
        itens.remove(mapaPerguntaDTO);
      } catch (final Exception ex) {
        log.log(Level.SEVERE, "Erro o gravar a materia e publicar.", ex);
      }
    }
  }

  @Async
  public void processarMaterias(final String uuid, final RequisicaoCaragMassaDTO request,
      final List<MapaPerguntaDTO> itens) {
    final LocalDate hoje = LocalDate.now();
    new File(properties.getCargaDadosImagens().getImagens().getPastaImagemMaterias()).mkdirs();

    LocalDateTime data = null;
    final Set<String> dataPublicadas = new HashSet<>();
    for (final HorarioRequisiscaoDTO hora : request.getHoarios()) {

      // Primeiro processa os com data
      final List<MapaPerguntaDTO> processarComData = itens.stream().filter(n -> nonNull(n.getDataSugestaoPublicacao()))
          .collect(Collectors.toList());
      for (final MapaPerguntaDTO mapaPerguntaDTO : processarComData) {

        // Se ele propos um ano firente do atual
        if (mapaPerguntaDTO.getDataSugestaoPublicacao().getYear() < hoje.getYear()
            || mapaPerguntaDTO.getDataSugestaoPublicacao().getYear() > (hoje.getYear() + 1)) {
          mapaPerguntaDTO.setDataSugestaoPublicacao(mapaPerguntaDTO.getDataSugestaoPublicacao()
              .plusYears(hoje.getYear() - mapaPerguntaDTO.getDataSugestaoPublicacao().getYear()));
        }

        // Se a data proposta for maior ou igual a hoje e esteja dentro do range de
        // inicio e fim, se não será ignorada.
        if ((mapaPerguntaDTO.getDataSugestaoPublicacao().isAfter(hoje)
            || mapaPerguntaDTO.getDataSugestaoPublicacao().isEqual(hoje))
            && (mapaPerguntaDTO.getDataSugestaoPublicacao().isAfter(request.getDataInicioPublicacao())
                && mapaPerguntaDTO.getDataSugestaoPublicacao().isAfter(request.getDataFimPublicacao()))) {
          data = SugerirMateriaUtils.getLocalDateTime(mapaPerguntaDTO.getDataSugestaoPublicacao(), hora.getHoraio());

          processarMateriaJoomla(data, dataPublicadas, mapaPerguntaDTO, uuid, request, itens);
        }
      }
    }
    for (final HorarioRequisiscaoDTO hora : request.getHoarios()) {
      final long rodar = nonNull(request.getDataFimPublicacao())
          ? SugerirMateriaUtils.getDaysBetween(request.getDataInicioPublicacao(), request.getDataFimPublicacao())
          : 1L;
      long rodaram = 0;
      while ((rodaram++) <= rodar && itens.size() > 0) {
        final MapaPerguntaDTO mapaPerguntaDTO = itens.get(0);
        data = SugerirMateriaUtils.getLocalDateTime(request.getDataInicioPublicacao(), hora.getHoraio(), rodaram);
        processarMateriaJoomla(data, dataPublicadas, mapaPerguntaDTO, uuid, request, itens);
      }
    }
  }

}