/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.JoomlaConfigurationProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.ItemResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.LinkResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.MetaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.CategoriaJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosCategoriaJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.CategoriaJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.CategoriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.CategoriaConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.CategoriaConvertImpl;

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
  JoomlaConfigurationProperties properties = new JoomlaConfigurationProperties("url", "bearer");

  @Spy
  CategoriaConvert convert = new CategoriaConvertImpl();

  /**
   * Test method for
   * {@link com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.CategoriaService#atualizarBancoCategoria()}.
   */
  @SuppressWarnings("unchecked")
  @Test
  void testAtualizarBancoCategoria() {

    when(categoriaJoomlaClient.getCategorias()).thenReturn(new ItemResponse<>(Arrays.asList(
        new CategoriaJoomlaDTO("type", "1",
            new AtributosCategoriaJoomlaDTO(1l, "title", "alias", "note", 1, 1, "checkedOut", "checkedOutTime", 1L,
                null, 1, 1, 1, "language", "description", "languageTitle", "languageImage", "editor", "access_level",
                "authorName", 1, 1, 1, 1)),
        new CategoriaJoomlaDTO("type", "2",
            new AtributosCategoriaJoomlaDTO(2l, "title", "alias", "note", 1, 1, "checkedOut", "checkedOutTime", 1L, 1L,
                1, 1, 1, "language", "description", "languageTitle", "languageImage", "editor", "access_level",
                "authorName", 1, 1, 1, 1)),
        new CategoriaJoomlaDTO("type", "2",
            new AtributosCategoriaJoomlaDTO(5l, "title", "alias", "note", 0, 1, "checkedOut", "checkedOutTime", 1L, 1L,
                1, 1, 1, "language", "description", "languageTitle", "languageImage", "editor", "access_level",
                "authorName", 1, 1, 1, 1))

    ), new LinkResponse("self", "url/next", "last", "first"), new MetaResponse(0l)));

    when(categoriaJoomlaClient.getCategorias(anyString(), anyString())).thenReturn(new ItemResponse<>(Arrays.asList(
        new CategoriaJoomlaDTO("type", "3",
            new AtributosCategoriaJoomlaDTO(3l, "title", "alias", "note", 1, 1, "checkedOut", "checkedOutTime", 1L, 1L,
                1, 1, 1, "language", "description", "languageTitle", "languageImage", "editor", "access_level",
                "authorName", 1, 1, 1, 1)),
        new CategoriaJoomlaDTO("type", "4",
            new AtributosCategoriaJoomlaDTO(4l, "title", "alias", "note", 1, 1, "checkedOut", "checkedOutTime", 1L, 1L,
                1, 1, 1, "language", "description", "languageTitle", "languageImage", "editor", "access_level",
                "authorName", 1, 1, 1, 1))

    ), new LinkResponse("self", null, "last", "first"), new MetaResponse(0l)));

    when(categoriaRepository.findByIdJoomla(anyLong())).thenReturn(Optional.empty(), Optional.empty(),
        Optional.of(new CategoriaEntity()), Optional.empty());

    final ArgumentCaptor<String> url1ArgumentCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<String> url2ArgumentCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<Long> idJoomlaArgumentCaptor = ArgumentCaptor.forClass(Long.class);
    final ArgumentCaptor<CategoriaEntity> categoriaEntityArgumentCaptor = ArgumentCaptor
        .forClass(CategoriaEntity.class);

    final int retorno = service.atualizarBancoCategoria();

    verify(categoriaJoomlaClient, times(1)).getCategorias();
    verify(categoriaJoomlaClient, times(1)).getCategorias(url1ArgumentCaptor.capture(), url2ArgumentCaptor.capture());
    verify(categoriaRepository, times(7)).findByIdJoomla(idJoomlaArgumentCaptor.capture());

    verify(categoriaRepository, times(4)).save(categoriaEntityArgumentCaptor.capture());

    assertEquals("20", url1ArgumentCaptor.getValue());
    assertEquals("20", url2ArgumentCaptor.getValue());
    assertEquals(4, retorno);

    assertEquals(1L, idJoomlaArgumentCaptor.getAllValues().get(0));
    assertEquals(2L, idJoomlaArgumentCaptor.getAllValues().get(1));
    assertEquals(1L, idJoomlaArgumentCaptor.getAllValues().get(2));
    assertEquals(3L, idJoomlaArgumentCaptor.getAllValues().get(3));
    assertEquals(1L, idJoomlaArgumentCaptor.getAllValues().get(4));
    assertEquals(4L, idJoomlaArgumentCaptor.getAllValues().get(5));
    assertEquals(1L, idJoomlaArgumentCaptor.getAllValues().get(6));

    assertEquals(1L, categoriaEntityArgumentCaptor.getAllValues().get(0).getIdJoomla());
    assertEquals(2L, categoriaEntityArgumentCaptor.getAllValues().get(1).getIdJoomla());
    assertEquals(3L, categoriaEntityArgumentCaptor.getAllValues().get(2).getIdJoomla());
    assertEquals(4L, categoriaEntityArgumentCaptor.getAllValues().get(3).getIdJoomla());

  }

}
