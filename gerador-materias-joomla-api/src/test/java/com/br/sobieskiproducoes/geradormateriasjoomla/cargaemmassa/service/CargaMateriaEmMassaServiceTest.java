package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.model.CargaMassaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.repository.CargaMassaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.RequisitaPerguntasDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.GerarMapaPerguntasService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class CargaMateriaEmMassaServiceTest {

    @Mock
    private GerarMapaPerguntasService gerarMapaPerguntasService;

    @Mock
    private CargaMassaRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CargaMateriaEmMassaService service;

    private RequisicaoCaragMassaDTO request;
    private List<MapaPerguntaDTO> expectedMapaPerguntas;
    private RequisitaPerguntasDTO requisitaPerguntas;

    @BeforeEach
    void setUp() {
        // Setup RequisitaPerguntasDTO
        requisitaPerguntas = new RequisitaPerguntasDTO();
        requisitaPerguntas.setTermos(Arrays.asList("Termo 1", "Termo 2"));
        
        // Setup RequisicaoCaragMassaDTO
        request = new RequisicaoCaragMassaDTO();
        request.setIdeias(requisitaPerguntas);
        request.setDataInicioPublicacao(LocalDate.now());
        request.setDataFimPublicacao(LocalDate.now().plusDays(7));
        request.setHorario("10:00");
        request.setPublicar(true);

        // Setup expected MapaPerguntaDTO
        MapaPerguntaDTO mapaPergunta = new MapaPerguntaDTO();
        expectedMapaPerguntas = Arrays.asList(mapaPergunta);
    }

    @Test
    void deveProcessarComSucesso() throws Exception {
        // Arrange
        when(gerarMapaPerguntasService.gerarMapa(any(RequisitaPerguntasDTO.class), any(String.class)))
            .thenReturn(expectedMapaPerguntas);
        when(objectMapper.writeValueAsString(any()))
            .thenReturn("{}");

        // Act
        List<MapaPerguntaDTO> result = service.processar(request);

        // Assert
        assertNotNull(result);
        assertEquals(expectedMapaPerguntas, result);
        verify(repository, times(1)).save(any(CargaMassaEntity.class));
    }

    @Test
    void deveProcessarMesmoQuandoFalharAoSalvar() throws Exception {
        // Arrange
        when(gerarMapaPerguntasService.gerarMapa(any(RequisitaPerguntasDTO.class), any(String.class)))
            .thenReturn(expectedMapaPerguntas);
        when(objectMapper.writeValueAsString(any()))
            .thenThrow(JsonProcessingException.class);

        // Act
        List<MapaPerguntaDTO> result = service.processar(request);

        // Assert
        assertNotNull(result);
        assertEquals(expectedMapaPerguntas, result);
    }

    @Test
    void deveLancarExcecaoQuandoGerarMapaFalhar() throws Exception {
        // Arrange
        when(gerarMapaPerguntasService.gerarMapa(any(RequisitaPerguntasDTO.class), any(String.class)))
            .thenThrow(new RuntimeException("Erro ao gerar mapa"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.processar(request));
    }

    @Test
    void deveProcessarComDataHoraCorreta() throws Exception {
        // Arrange
        when(gerarMapaPerguntasService.gerarMapa(any(RequisitaPerguntasDTO.class), any(String.class)))
            .thenReturn(expectedMapaPerguntas);
        when(objectMapper.writeValueAsString(any()))
            .thenReturn("{}");

        // Act
        service.processar(request);

        // Assert
        verify(repository).save(any(CargaMassaEntity.class));
    }
}
