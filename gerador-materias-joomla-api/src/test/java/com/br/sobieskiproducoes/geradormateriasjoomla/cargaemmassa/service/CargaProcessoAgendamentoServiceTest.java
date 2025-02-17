package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.model.CargaMassaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.repository.CargaMassaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository.MapaPerguntaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class CargaProcessoAgendamentoServiceTest {

    @Mock
    private CargaMassaRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private MapaPerguntaRepository mapaPerguntaRepository;

    @Mock
    private ProcessamentoCriarMateriasService processoCriacaoMaterias;

    @Mock
    private ProcessamentoPublicarMateriasService processamentoPublicarMateriasService;

    @InjectMocks
    private CargaProcessoAgendamentoService service;

    private CargaMassaEntity cargaMassaEntity;
    private RequisicaoCaragMassaDTO requisicaoDTO;
    private String jsonRequisicao;

    @BeforeEach
    void setUp() throws Exception {
        cargaMassaEntity = new CargaMassaEntity();
        cargaMassaEntity.setUuid("test-uuid");
        cargaMassaEntity.setStatus(StatusProcessamentoEnum.PROCESSAR);
        
        requisicaoDTO = new RequisicaoCaragMassaDTO();
        jsonRequisicao = "{}"; // JSON simplificado para teste
        
        cargaMassaEntity.setRequisicao(jsonRequisicao);
    }

    @Test
    void deveProcessarCriacaoMateriasComSucesso() throws Exception {
        // Arrange
        List<CargaMassaEntity> cargas = Arrays.asList(cargaMassaEntity);
        when(repository.pegarCarga()).thenReturn(cargas);
        when(objectMapper.readValue(jsonRequisicao, RequisicaoCaragMassaDTO.class)).thenReturn(requisicaoDTO);
        when(processoCriacaoMaterias.iniciarProcesso(any(RequisicaoCaragMassaDTO.class), anyString())).thenReturn(true);
        when(mapaPerguntaRepository.totalAProcessar(anyString())).thenReturn(0L);

        // Act
        service.processarCriacaoMaterias();

        // Assert
        verify(repository).save(any(CargaMassaEntity.class));
        verify(processoCriacaoMaterias).iniciarProcesso(any(RequisicaoCaragMassaDTO.class), anyString());
    }

    @Test
    void deveMarcarComoErroQuandoProcessamentoFalhar() throws Exception {
        // Arrange
        List<CargaMassaEntity> cargas = Arrays.asList(cargaMassaEntity);
        when(repository.pegarCarga()).thenReturn(cargas);
        when(objectMapper.readValue(jsonRequisicao, RequisicaoCaragMassaDTO.class)).thenReturn(requisicaoDTO);
        when(processoCriacaoMaterias.iniciarProcesso(any(RequisicaoCaragMassaDTO.class), anyString())).thenReturn(false);

        // Act
        service.processarCriacaoMaterias();

        // Assert
        verify(repository).save(any(CargaMassaEntity.class));
        verify(mapaPerguntaRepository, never()).totalAProcessar(anyString());
    }

    @Test
    void deveContinuarProcessamentoMesmoComErro() throws Exception {
        // Arrange
        List<CargaMassaEntity> cargas = Arrays.asList(cargaMassaEntity);
        when(repository.pegarCarga()).thenReturn(cargas);
        when(objectMapper.readValue(jsonRequisicao, RequisicaoCaragMassaDTO.class))
            .thenThrow(new RuntimeException("Erro de teste"));

        // Act
        service.processarCriacaoMaterias();

        // Assert
        verify(repository).save(any(CargaMassaEntity.class));
        verify(processoCriacaoMaterias, never()).iniciarProcesso(any(), any());
    }

    @Test
    void naoDeveProcessarQuandoNaoHouverCargas() throws Exception {
        // Arrange
        when(repository.pegarCarga()).thenReturn(Collections.emptyList());

        // Act
        service.processarCriacaoMaterias();

        // Assert
        verify(repository, never()).save(any(CargaMassaEntity.class));
        verify(processoCriacaoMaterias, never()).iniciarProcesso(any(), any());
    }

    @Test
    void deveProcessarPublicacaoMateriaComSucesso() throws BusinessException {
        // Act
        service.processarPublicacaoMateria();

        // Assert
        verify(processamentoPublicarMateriasService).processar();
    }

    @Test
    void deveAtualizarStatusParaProcessadoQuandoNaoHouverMaisItens() throws Exception {
        // Arrange
        List<CargaMassaEntity> cargas = Arrays.asList(cargaMassaEntity);
        when(repository.pegarCarga()).thenReturn(cargas);
        when(objectMapper.readValue(jsonRequisicao, RequisicaoCaragMassaDTO.class)).thenReturn(requisicaoDTO);
        when(processoCriacaoMaterias.iniciarProcesso(any(RequisicaoCaragMassaDTO.class), anyString())).thenReturn(true);
        when(mapaPerguntaRepository.totalAProcessar(anyString())).thenReturn(0L);

        // Act
        service.processarCriacaoMaterias();

        // Assert
        verify(repository).save(any(CargaMassaEntity.class));
        verify(mapaPerguntaRepository).totalAProcessar(anyString());
    }

    @Test
    void deveManterProcessamentoQuandoPublicacaoFalhar() throws BusinessException {
        // Arrange
        doThrow(new RuntimeException("Erro ao publicar"))
            .when(processamentoPublicarMateriasService)
            .processar();

        // Act
        service.processarPublicacaoMateria();

        // Assert
        verify(processamentoPublicarMateriasService).processar();
    }
}
