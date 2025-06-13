/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sobieskiproducoes.geradormaterias.config.properties.CargaDadosImagensProperties;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ChatGPTConfigurationProperties;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormaterias.config.properties.DadosImagensLogoProperties;
import com.br.sobieskiproducoes.geradormaterias.config.properties.DadosImagensMateriasProperties;
import com.br.sobieskiproducoes.geradormaterias.config.properties.JoomlaConfigurationProperties;
import com.br.sobieskiproducoes.geradormaterias.config.properties.PosicaoEnum;
import com.br.sobieskiproducoes.geradormaterias.config.properties.YoutubeConfigurationProperties;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.LinkResponse;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.MetaResponse;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.CategoriaJoomlaClient;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosCategoriaJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.repository.CategoriaRepository;
import com.br.sobieskiproducoes.geradormaterias.materia.service.convert.CategoriaConvert;
import com.br.sobieskiproducoes.geradormaterias.materia.service.convert.CategoriaConvertImpl;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 20:18:55
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

  @InjectMocks
  CategoriaService service;

  @Mock
  CategoriaRepository categoriaRepository;

  @Mock
  CategoriaJoomlaClient categoriaJoomlaClient;

  @Spy
  ConfiguracoesProperties properties = new ConfiguracoesProperties(
      new CargaDadosImagensProperties(new DadosImagensMateriasProperties("pastaImagemMaterias", "autor", 1, 1, 0.3, "constante", "url", Boolean.TRUE),
          new DadosImagensLogoProperties("path", PosicaoEnum.ALEATORIO, PosicaoEnum.ALEATORIO, 1, 1, 0.4d, 1, 0.4d)),
      new ChatGPTConfigurationProperties(

          "urlCriarThrend", "urlPostarMensagem", "urlIniciarRun", "urlLerRunner", "urlAvancarMessage", "urlLerMensagem", "urlApagarThrend", "thread",

          "url", "bearer", "assistente", "organization", "model", 0.4d, "roleUser", "roleSystem", "roleAssistant", "maxTokens"),
      new JoomlaConfigurationProperties("url", "bearer", "idioma"), new YoutubeConfigurationProperties());

  @Spy
  CategoriaConvert convert = new CategoriaConvertImpl();

  /**
   * Test method for {@link com.br.sobieskiproducoes.geradormaterias.materia.service.CategoriaService#atualizarBancoCategoria()}.
   */
  @Test
  void testAtualizarBancoCategoria() {

    when(categoriaJoomlaClient.getCategorias()).thenReturn(new GenericoItemJoomlaResponse<>(Arrays.asList(
        new GenericoJoomlaDataDTO<>("type", "1",
            new AtributosCategoriaJoomlaDTO(1L, "title", "alias", "note", 1, 1, "checkedOut", "checkedOutTime", 1L, null, 1, 1, 1, "language", "description",
                "languageTitle", "languageImage", "editor", "access_level", "authorName", 1, 1, 1, 1)),
        new GenericoJoomlaDataDTO<>("type", "2",
            new AtributosCategoriaJoomlaDTO(2L, "title", "alias", "note", 1, 1, "checkedOut", "checkedOutTime", 1L, 1L, 1, 1, 1, "language", "description",
                "languageTitle", "languageImage", "editor", "access_level", "authorName", 1, 1, 1, 1)),
        new GenericoJoomlaDataDTO<>("type", "2", new AtributosCategoriaJoomlaDTO(5L, "title", "alias", "note", 0, 1, "checkedOut", "checkedOutTime", 1L, 1L, 1,
            1, 1, "language", "description", "languageTitle", "languageImage", "editor", "access_level", "authorName", 1, 1, 1, 1))

    ), new LinkResponse("self", "url/next", "last", "first", "previous"), new MetaResponse(0L)));

    when(categoriaJoomlaClient.getCategorias(anyString(), anyString())).thenReturn(new GenericoItemJoomlaResponse<>(Arrays.asList(
        new GenericoJoomlaDataDTO<>("type", "3",
            new AtributosCategoriaJoomlaDTO(3L, "title", "alias", "note", 1, 1, "checkedOut", "checkedOutTime", 1L, 1L, 1, 1, 1, "language", "description",
                "languageTitle", "languageImage", "editor", "access_level", "authorName", 1, 1, 1, 1)),
        new GenericoJoomlaDataDTO<>("type", "4", new AtributosCategoriaJoomlaDTO(4L, "title", "alias", "note", 1, 1, "checkedOut", "checkedOutTime", 1L, 1L, 1,
            1, 1, "language", "description", "languageTitle", "languageImage", "editor", "access_level", "authorName", 1, 1, 1, 1))

    ), new LinkResponse("self", null, "last", "first", "previous"), new MetaResponse(0L)));

//    when(categoriaRepository.findByIdJoomla(anyLong())).thenReturn(Optional.empty(), Optional.empty(),
//        Optional.of(new CategoriaEntity()), Optional.empty());

    final ArgumentCaptor<String> url1ArgumentCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<String> url2ArgumentCaptor = ArgumentCaptor.forClass(String.class);
    // final ArgumentCaptor<Long> idJoomlaArgumentCaptor =
    // ArgumentCaptor.forClass(Long.class);
//
//    final ArgumentCaptor<CategoriaEntity> categoriaEntityArgumentCaptor = ArgumentCaptor
//        .forClass(CategoriaEntity.class);

    final var retornoItem = service.atualizarBancoCategoria();

    verify(categoriaJoomlaClient, times(1)).getCategorias();
    verify(categoriaJoomlaClient, times(1)).getCategorias(url1ArgumentCaptor.capture(), url2ArgumentCaptor.capture());
//    verify(categoriaRepository, times(7)).findByIdJoomla(idJoomlaArgumentCaptor.capture());

//    verify(categoriaRepository, times(4)).save(categoriaEntityArgumentCaptor.capture());

    assertEquals("20", url1ArgumentCaptor.getValue());
    assertEquals("20", url2ArgumentCaptor.getValue());
    assertEquals(0, retornoItem.get("total"));

//    assertEquals(1L, idJoomlaArgumentCaptor.getAllValues().get(0));
//    assertEquals(2L, idJoomlaArgumentCaptor.getAllValues().get(1));
//    assertEquals(1L, idJoomlaArgumentCaptor.getAllValues().get(2));
//    assertEquals(3L, idJoomlaArgumentCaptor.getAllValues().get(3));
//    assertEquals(1L, idJoomlaArgumentCaptor.getAllValues().get(4));
//    assertEquals(4L, idJoomlaArgumentCaptor.getAllValues().get(5));
//    assertEquals(1L, idJoomlaArgumentCaptor.getAllValues().get(6));

//    assertEquals(1L, categoriaEntityArgumentCaptor.getAllValues().get(0).getIdJoomla());
//    assertEquals(2L, categoriaEntityArgumentCaptor.getAllValues().get(1).getIdJoomla());
//    assertEquals(3L, categoriaEntityArgumentCaptor.getAllValues().get(2).getIdJoomla());
//    assertEquals(4L, categoriaEntityArgumentCaptor.getAllValues().get(3).getIdJoomla());

  }

}
