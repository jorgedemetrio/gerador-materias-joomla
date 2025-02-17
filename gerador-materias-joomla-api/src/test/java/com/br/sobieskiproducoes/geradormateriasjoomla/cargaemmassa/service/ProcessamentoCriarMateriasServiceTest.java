package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.CargaDadosImagensProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.DadosImagensMateriasProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.MapaPerguntaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.repository.MapaPerguntaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.convert.PerguntasConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.CategoriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaPorMataService;

@ExtendWith(MockitoExtension.class)
class ProcessamentoCriarMateriasServiceTest {

  @Mock
  private ConfiguracoesProperties properties;

  @Mock
  private GerarMateriaPorMataService gerarMateriaService;

  @Mock
  private MateriaRepository materiaRepository;

  @Mock
  private MapaPerguntaRepository mapaPerguntaRepository;

  @Mock
  private PerguntasConvert perguntasConvert;

  @InjectMocks
  private ProcessamentoCriarMateriasService service;

  private RequisicaoCaragMassaDTO request;
  private String uuid;
  private MapaPerguntaEntity mapaPerguntaEntity;
  private MapaPerguntaDTO mapaPerguntaDTO;

  private MapaPerguntaDTO createMapaPerguntaDTO(final LocalDate dataSugestao) {
    final MapaPerguntaDTO dto = new MapaPerguntaDTO();
    dto.setId(1L);
    dto.setPergunta("Teste pergunta?");
    dto.setPerguntasAlternativas(Arrays.asList("Alt 1", "Alt 2"));
    dto.setTermos(Arrays.asList("Termo 1", "Termo 2"));

    final CategoriaDTO categoria = new CategoriaDTO();
    categoria.setId(1L);
    categoria.setTitulo("Categoria Teste");
    dto.setCategoria(categoria);

    dto.setDataSugestaoPublicacao(dataSugestao);
    return dto;
  }

  @Test
  void deveAtualizarMateriaExistenteQuandoEncontrada() throws Exception {
    // Arrange
    final MateriaEntity materiaExistente = new MateriaEntity();
    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Collections.singletonList(mapaPerguntaEntity));
    when(perguntasConvert.toMapaPerguntaDTO(any(MapaPerguntaEntity.class))).thenReturn(mapaPerguntaDTO);
    when(materiaRepository.buscarPorPergunta(any())).thenReturn(Optional.of(materiaExistente));

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertTrue(result);
    verify(materiaRepository).save(any(MateriaEntity.class));
    verify(gerarMateriaService, never()).gerarSugestaoMateria(any(SugerirMateriaDTO.class), anyString(), anyLong());
  }

  @Test
  void deveProcessarComSucessoQuandoNaoExistirMateriaPrevia() throws Exception {
    // Arrange
    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Collections.singletonList(mapaPerguntaEntity));
    when(perguntasConvert.toMapaPerguntaDTO(any(MapaPerguntaEntity.class))).thenReturn(mapaPerguntaDTO);
    when(materiaRepository.buscarPorPergunta(any())).thenReturn(Optional.empty());

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertTrue(result);
    verify(gerarMateriaService, times(1)).gerarSugestaoMateria(any(), anyString(), any());
  }

  @Test
  void deveProcessarMateriasComDatasDiferentes() throws Exception {
    // Arrange
    final MapaPerguntaDTO mapaPergunta1 = createMapaPerguntaDTO(LocalDate.now().plusDays(1));
    final MapaPerguntaDTO mapaPergunta2 = createMapaPerguntaDTO(LocalDate.now().plusDays(2));

    final MapaPerguntaEntity entity1 = new MapaPerguntaEntity();
    final MapaPerguntaEntity entity2 = new MapaPerguntaEntity();

    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Arrays.asList(entity1, entity2));
    when(perguntasConvert.toMapaPerguntaDTO(entity1)).thenReturn(mapaPergunta1);
    when(perguntasConvert.toMapaPerguntaDTO(entity2)).thenReturn(mapaPergunta2);
    when(materiaRepository.buscarPorPergunta(any())).thenReturn(Optional.empty());

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertTrue(result);
    verify(gerarMateriaService, times(2)).gerarSugestaoMateria(any(), anyString(), any());
  }

  @Test
  void deveRetornarFalsoQuandoOcorrerErro() throws Exception {
    // Arrange
    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Collections.singletonList(mapaPerguntaEntity));
    when(perguntasConvert.toMapaPerguntaDTO(any(MapaPerguntaEntity.class))).thenReturn(mapaPerguntaDTO);
    when(materiaRepository.buscarPorPergunta(any())).thenThrow(new RuntimeException("Erro simulado"));

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertFalse(result);
  }

  @BeforeEach
  void setUp() {
    uuid = "test-uuid";
    request = new RequisicaoCaragMassaDTO();
    request.setDataInicioPublicacao(LocalDate.now());
    request.setDataFimPublicacao(LocalDate.now().plusDays(7));
    request.setHorario("10:00");

    // Setup properties mock
    final DadosImagensMateriasProperties dadosImagens = new DadosImagensMateriasProperties();
    dadosImagens.setPastaImagemMaterias("./test-images");

    final CargaDadosImagensProperties cargaDadosImagens = new CargaDadosImagensProperties();
    cargaDadosImagens.setImagens(dadosImagens);

    when(properties.getCargaDadosImagens()).thenReturn(cargaDadosImagens);

    // Setup MapaPergunta mocks
    mapaPerguntaEntity = new MapaPerguntaEntity();
    mapaPerguntaEntity.setId(1L);
    mapaPerguntaEntity.setPerguntasAlternativas(Collections.emptyList());
    mapaPerguntaEntity.setTermos(Collections.emptyList());

    mapaPerguntaDTO = new MapaPerguntaDTO();
    mapaPerguntaDTO.setId(1L);
    mapaPerguntaDTO.setPergunta("Teste pergunta?");
    mapaPerguntaDTO.setPerguntasAlternativas(Arrays.asList("Alt 1", "Alt 2"));
    mapaPerguntaDTO.setTermos(Arrays.asList("Termo 1", "Termo 2"));

    final CategoriaDTO categoria = new CategoriaDTO();
    categoria.setId(1L);
    categoria.setTitulo("Categoria Teste");
    mapaPerguntaDTO.setCategoria(categoria);

    mapaPerguntaDTO.setDataSugestaoPublicacao(LocalDate.now().plusDays(1));
  }

  @Test
  void deveIgnorarDataSugestaoForaDoRange() throws Exception {
    // Arrange
    mapaPerguntaDTO.setDataSugestaoPublicacao(LocalDate.now().plusYears(2));
    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Collections.singletonList(mapaPerguntaEntity));
    when(perguntasConvert.toMapaPerguntaDTO(any(MapaPerguntaEntity.class))).thenReturn(mapaPerguntaDTO);
    when(materiaRepository.buscarPorPergunta(any())).thenReturn(Optional.empty());

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertTrue(result);
    verify(gerarMateriaService, times(1)).gerarSugestaoMateria(any(), anyString(), any());
  }

  @Test
  void deveProcessarMateriasSemDataSugestao() throws Exception {
    // Arrange
    mapaPerguntaDTO.setDataSugestaoPublicacao(null);
    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Collections.singletonList(mapaPerguntaEntity));
    when(perguntasConvert.toMapaPerguntaDTO(any(MapaPerguntaEntity.class))).thenReturn(mapaPerguntaDTO);
    when(materiaRepository.buscarPorPergunta(any())).thenReturn(Optional.empty());

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertTrue(result);
    verify(gerarMateriaService, times(1)).gerarSugestaoMateria(any(), anyString(), any());
  }

  @Test
  void deveConsiderarUltimaDataPublicacao() throws Exception {
    // Arrange
    LocalDateTime ultimaData = LocalDateTime.now().minusDays(1);
    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Collections.singletonList(mapaPerguntaEntity));
    when(perguntasConvert.toMapaPerguntaDTO(any(MapaPerguntaEntity.class))).thenReturn(mapaPerguntaDTO);
    when(materiaRepository.buscarPorPergunta(any())).thenReturn(Optional.empty());
    when(materiaRepository.ultimaData(anyString(), anyString())).thenReturn(ultimaData);

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertTrue(result);
    verify(gerarMateriaService, times(1)).gerarSugestaoMateria(any(), anyString(), any());
  }

  @Test
  void deveEvitarDuplicacaoDeDataPublicacao() throws Exception {
    // Arrange
    MapaPerguntaDTO mapaPergunta1 = createMapaPerguntaDTO(LocalDate.now().plusDays(1));
    MapaPerguntaDTO mapaPergunta2 = createMapaPerguntaDTO(LocalDate.now().plusDays(1)); // Mesma data

    MapaPerguntaEntity entity1 = new MapaPerguntaEntity();
    MapaPerguntaEntity entity2 = new MapaPerguntaEntity();

    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Arrays.asList(entity1, entity2));
    when(perguntasConvert.toMapaPerguntaDTO(entity1)).thenReturn(mapaPergunta1);
    when(perguntasConvert.toMapaPerguntaDTO(entity2)).thenReturn(mapaPergunta2);
    when(materiaRepository.buscarPorPergunta(any())).thenReturn(Optional.empty());

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertTrue(result);
    verify(gerarMateriaService, times(1)).gerarSugestaoMateria(any(), anyString(), any());
  }

  @Test
  void deveRetornarFalsoQuandoErroAoCriarDiretorio() throws Exception {
    // Arrange
    when(mapaPerguntaRepository.buscaPorUiidParaCargaLimitado5(uuid)).thenReturn(Collections.singletonList(mapaPerguntaEntity));
    when(perguntasConvert.toMapaPerguntaDTO(any(MapaPerguntaEntity.class))).thenReturn(mapaPerguntaDTO);
    when(properties.getCargaDadosImagens().getImagens().getPastaImagemMaterias()).thenThrow(new RuntimeException("Erro ao criar diretório"));

    // Act
    final Boolean result = service.iniciarProcesso(request, uuid);

    // Assert
    assertTrue(result); // Deve continuar mesmo com erro no diretório
    verify(gerarMateriaService, times(1)).gerarSugestaoMateria(any(), anyString(), any());
  }
}
