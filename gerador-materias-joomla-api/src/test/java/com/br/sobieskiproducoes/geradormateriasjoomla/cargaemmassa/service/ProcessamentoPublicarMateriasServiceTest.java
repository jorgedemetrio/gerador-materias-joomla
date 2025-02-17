package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.CargaDadosImagensProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.DadosImagensMateriasProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoSalvoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.BusinessException;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.CategoriaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaJoomlaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.TagService;

@ExtendWith(MockitoExtension.class)
class ProcessamentoPublicarMateriasServiceTest {

    @Mock
    private ConfiguracoesProperties properties;

    @Mock
    private MateriaJoomlaService materiaJoomlaService;

    @Mock
    private MateriaRepository materiaRepository;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private TagService tagService;

    @InjectMocks
    private ProcessamentoPublicarMateriasService service;

    private MateriaEntity materiaEntity;
    private CategoriaEntity categoriaEntity;
    private GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> response;

    @BeforeEach
    void setUp() {
        // Setup properties mock
        DadosImagensMateriasProperties dadosImagens = new DadosImagensMateriasProperties();
        dadosImagens.setPastaImagemMaterias("./test-images");
        
        CargaDadosImagensProperties cargaDadosImagens = new CargaDadosImagensProperties();
        cargaDadosImagens.setImagens(dadosImagens);
        
        when(properties.getCargaDadosImagens()).thenReturn(cargaDadosImagens);

        // Setup materia entity
        categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1L);
        categoriaEntity.setTitulo("Categoria Teste");

        materiaEntity = new MateriaEntity();
        materiaEntity.setId(1L);
        materiaEntity.setStatus(StatusProcessamentoEnum.PROCESSAR);
        materiaEntity.setCategoria(categoriaEntity);
        materiaEntity.setMateria("Conteúdo da matéria");

        // Setup response mock
        response = new GenericoItemJoomlaResponse<>();
        GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO> data = new GenericoJoomlaDataDTO<>();
        AtributosArtigoSalvoJoomlaDTO attributes = new AtributosArtigoSalvoJoomlaDTO();
        attributes.setId(1L);
        data.setAttributes(attributes);
        response.setData(data);
    }

    @Test
    void deveProcessarMateriaComSucesso() throws BusinessException {
        // Arrange
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity));
        when(materiaJoomlaService.publicarMateriaJoomla(any(MateriaEntity.class)))
            .thenReturn(response);

        // Act
        service.processar();

        // Assert
        verify(categoriaService).atualizarBancoCategoria();
        verify(tagService).atualizarBancoTag();
        verify(materiaJoomlaService).publicarMateriaJoomla(materiaEntity);
    }

    @Test
    void naoDeveProcessarQuandoNaoHouverMaterias() throws BusinessException {
        // Arrange
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Collections.emptyList());

        // Act
        service.processar();

        // Assert
        verify(categoriaService, never()).atualizarBancoCategoria();
        verify(tagService, never()).atualizarBancoTag();
        verify(materiaJoomlaService, never()).publicarMateriaJoomla(any(MateriaEntity.class));
    }

    @Test
    void naoDeveProcessarMateriaJaProcessada() throws BusinessException {
        // Arrange
        materiaEntity.setStatus(StatusProcessamentoEnum.PROCESSADO);
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity));

        // Act
        service.processar();

        // Assert
        verify(categoriaService).atualizarBancoCategoria();
        verify(tagService).atualizarBancoTag();
        verify(materiaJoomlaService, never()).publicarMateriaJoomla(any(MateriaEntity.class));
    }

    @Test
    void deveProcessarMultiplasMaterias() throws BusinessException {
        // Arrange
        MateriaEntity materiaEntity2 = new MateriaEntity();
        materiaEntity2.setId(2L);
        materiaEntity2.setStatus(StatusProcessamentoEnum.PROCESSAR);
        materiaEntity2.setCategoria(categoriaEntity);
        materiaEntity2.setMateria("Conteúdo da matéria 2");

        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity, materiaEntity2));
        when(materiaJoomlaService.publicarMateriaJoomla(any(MateriaEntity.class)))
            .thenReturn(response);

        // Act
        service.processar();

        // Assert
        verify(categoriaService, times(1)).atualizarBancoCategoria();
        verify(tagService, times(1)).atualizarBancoTag();
        verify(materiaJoomlaService, times(2)).publicarMateriaJoomla(any(MateriaEntity.class));
    }

    @Test
    void deveContinuarProcessamentoMesmoComErroNaCriacaoDePasta() throws BusinessException {
        // Arrange
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity));
        when(materiaJoomlaService.publicarMateriaJoomla(materiaEntity))
            .thenReturn(response);
        when(properties.getCargaDadosImagens().getImagens().getPastaImagemMaterias())
            .thenThrow(new RuntimeException("Erro ao criar pasta"));

        // Act
        service.processar();

        // Assert
        verify(materiaJoomlaService).publicarMateriaJoomla(materiaEntity);
    }

    @Test
    void deveProcessarQuandoPublicacaoRetornarNull() throws BusinessException {
        // Arrange
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity));
        when(materiaJoomlaService.publicarMateriaJoomla(any(MateriaEntity.class)))
            .thenReturn(null);

        // Act
        service.processar();

        // Assert
        verify(materiaJoomlaService).publicarMateriaJoomla(materiaEntity);
    }

    @Test
    void deveProcessarQuandoMateriaForNula() throws BusinessException {
        // Arrange
        MateriaEntity materiaNula = null;
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaNula, materiaEntity));
        when(materiaJoomlaService.publicarMateriaJoomla(any(MateriaEntity.class)))
            .thenReturn(response);

        // Act
        service.processar();

        // Assert
        verify(materiaJoomlaService, times(1)).publicarMateriaJoomla(materiaEntity);
    }


    @Test
    void deveContinuarProcessamentoQuandoPublicacaoFalhar() throws BusinessException {
        // Arrange
        MateriaEntity materiaEntity2 = new MateriaEntity();
        materiaEntity2.setId(2L);
        materiaEntity2.setStatus(StatusProcessamentoEnum.PROCESSAR);
        materiaEntity2.setCategoria(categoriaEntity);

        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity, materiaEntity2));
        when(materiaJoomlaService.publicarMateriaJoomla(materiaEntity))
            .thenThrow(new RuntimeException("Erro ao publicar"));
        when(materiaJoomlaService.publicarMateriaJoomla(materiaEntity2))
            .thenReturn(response);

        // Act
        service.processar();

        // Assert
        verify(materiaJoomlaService).publicarMateriaJoomla(materiaEntity2);
    }

    @Test
    void deveContinuarQuandoAtualizacaoDeBancoFalhar() throws BusinessException {
        // Arrange
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity));
        when(categoriaService.atualizarBancoCategoria())
            .thenThrow(new RuntimeException("Erro ao atualizar banco"));
        when(materiaJoomlaService.publicarMateriaJoomla(any(MateriaEntity.class)))
            .thenReturn(response);

        // Act
        service.processar();

        // Assert
        verify(materiaJoomlaService).publicarMateriaJoomla(materiaEntity);
    }

    @Test
    void deveProcessarQuandoListaMateriasForNula() throws BusinessException {
        // Arrange
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(null);

        // Act
        service.processar();

        // Assert
        verify(categoriaService, never()).atualizarBancoCategoria();
        verify(tagService, never()).atualizarBancoTag();
        verify(materiaJoomlaService, never()).publicarMateriaJoomla(any(MateriaEntity.class));
    }

    @Test
    void deveProcessarQuandoPathCategoriaRetornarNulo() throws BusinessException {
        // Arrange
        materiaEntity.setCategoria(null);
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity));
        when(materiaJoomlaService.publicarMateriaJoomla(any(MateriaEntity.class)))
            .thenReturn(response);

        // Act
        service.processar();

        // Assert
        verify(materiaJoomlaService).publicarMateriaJoomla(materiaEntity);
    }

    @Test
    void deveProcessarQuandoIdJoomlaForNulo() throws BusinessException {
        // Arrange
        materiaEntity.setIdJoomla(null);
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity));
        when(materiaJoomlaService.publicarMateriaJoomla(any(MateriaEntity.class)))
            .thenReturn(response);

        // Act
        service.processar();

        // Assert
        verify(materiaJoomlaService).publicarMateriaJoomla(materiaEntity);
    }

    @Test
    void deveProcessarQuandoIdForNulo() throws BusinessException {
        // Arrange
        materiaEntity.setId(null);
        when(materiaRepository.buscarMateriasPublicar())
            .thenReturn(Arrays.asList(materiaEntity));
        when(materiaJoomlaService.publicarMateriaJoomla(any(MateriaEntity.class)))
            .thenReturn(response);

        // Act
        service.processar();

        // Assert
        verify(materiaJoomlaService).publicarMateriaJoomla(materiaEntity);
    }
}
