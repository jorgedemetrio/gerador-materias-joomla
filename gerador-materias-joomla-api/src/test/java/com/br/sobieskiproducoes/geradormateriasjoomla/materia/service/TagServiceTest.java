/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.TagJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosTagJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.TagDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.TagEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.TagRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.TagConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.TagConvertImpl;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 01:05:36
 * @version 1.0.0
 * @param <MateriaProperties>
 */
@ExtendWith(MockitoExtension.class)
class TagServiceTest{

  @InjectMocks
  TagService service;

  @Mock
  TagRepository repository;

  @Spy
  TagConvert convert = new TagConvertImpl();

  @Mock
  TagJoomlaClient client;
  
  @Spy
  MateriaProperties properties = new MateriaProperties();
  

  @Test
  void apagarTest_TagEntityNotFound() {

    final Long id = 10L;
    final Optional<TagEntity> tagEntityOpt = Optional.empty();
    when(repository.findById(id)).thenReturn(tagEntityOpt);
    assertFalse(service.apagar(id));
    // assertThrows(BusinessException.class, () -> service.apagar(1L));
  }

  @Test
  void buscarTest() {
    // cenário
    final Long id = 10L;
    final String titulo = "Titulo";
    final TagEntity tag = new TagEntity();
    tag.setTitulo(titulo);
    tag.setId(id);
    final Optional<TagEntity> tagEntityOpt = Optional.of(tag);
    when(repository.findById(anyLong())).thenReturn(tagEntityOpt);

    // Chamada do teste
    // o objeto TagDTO retornado pelo metodo é armazenado na variavel TagDTO
    // um novo objeto tagdto chamado expectedtagdto é criado com os valores
    // esperados
    // e a função assertequals compara o objt tagdto com o expectedtagdto
    final TagDTO tagDTO = service.buscarPorId(id);

    // Valida o processo

    final ArgumentCaptor<Long> capIdLong = ArgumentCaptor.forClass(Long.class);
    verify(repository, times(1)).findById(capIdLong.capture());
    assertEquals(id, capIdLong.getValue());

    // se os dois objetos forem iguais, o teste será bem sucedido.
    assertEquals(id, tagDTO.getId());
    assertEquals(titulo, tagDTO.getTitulo());

  }

  @Test
  void tesBuscarTestTagEntityNotFound() {

    final Long id = 10L;

    final Optional<TagEntity> tagEntityOpt = Optional.empty();
    when(repository.findById(anyLong())).thenReturn(tagEntityOpt);

    assertNull(service.buscarPorId(id));

    final ArgumentCaptor<Long> capIdLong = ArgumentCaptor.forClass(Long.class);
    verify(repository, times(1)).findById(capIdLong.capture());

    assertEquals(id, capIdLong.getValue());
  }

  @Test
  void testApagar() {
    // Cenário
    final Long id = 10L;
    final String titulo = "Titulo";
    final TagEntity tag = new TagEntity();
    tag.setId(id);
    tag.setTitulo(titulo);
    final Optional<TagEntity> tagEntityOpt = Optional.of(tag);
    when(repository.findById(anyLong())).thenReturn(tagEntityOpt);

    // Chamada do teste, como ele retorna um objeto simples já estou validadndo se
    // está retorando True.
    // Se fosse um objeto complexo receberia em uma variavel e validaria cara
    // atributo.
    assertTrue(service.apagar(id));

    // Validação do resultado do teste e suas chamadas internas
    final ArgumentCaptor<TagEntity> capTagEntity = ArgumentCaptor.forClass(TagEntity.class);
    final ArgumentCaptor<Long> capIdLong = ArgumentCaptor.forClass(Long.class);

    verify(repository, times(1)).delete(capTagEntity.capture());
    verify(repository, times(1)).findById(capIdLong.capture());

    assertEquals(id, capIdLong.getValue());
    assertEquals(id, capTagEntity.getValue().getId());
    assertEquals(titulo, capTagEntity.getValue().getTitulo());
  }

  @Test
  void testGravar() throws Exception {

      // Preparação
      final Long id = 10L;
      final String titulo = "Titulo Atualizado";
      final TagDTO tagDTO = new TagDTO(id, 11L, "uuid", titulo, null);
      final TagEntity entity = new TagEntity(10L, "uuid", 11L, titulo, "apelido", "language", null);



      when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
      when(repository.save(any(TagEntity.class))).thenReturn(entity);


      // Ação
      final TagDTO tagDTOGravada = service.gravar(tagDTO);

      // Verificação
      final ArgumentCaptor<Long> captorLongId  = ArgumentCaptor.forClass(Long.class);
      final ArgumentCaptor<TagEntity> captorTagEntity  = ArgumentCaptor.forClass(TagEntity.class);

      verify(repository, times(1)).save(captorTagEntity.capture());
      verify(repository, times(1)).findById(captorLongId.capture());

      assertEquals(10L, captorLongId.getValue());
      assertEquals(titulo, captorTagEntity.getValue().getTitulo());
      assertEquals(10L, captorTagEntity.getValue().getId());
      assertEquals("uuid", captorTagEntity.getValue().getUuid());
      assertEquals(11L, captorTagEntity.getValue().getIdJoomla());

      assertThat(tagDTOGravada.getId()).isEqualTo(id);
      assertThat(tagDTOGravada.getTitulo()).isEqualTo(titulo);



  }

  @Test
  void testGravarIdNull() throws Exception {

    // Preparação
    final String titulo = "Nova Tag";
    final TagDTO tagDTO = new TagDTO(null, 11L, "uuid", titulo, "apelido");

    when(repository.save(any(TagEntity.class)))
        .thenReturn(new TagEntity(10L, "uuid", 10L, titulo, "apelido", "language", null));

    // Ação
    final TagDTO tagDTOGravada = service.gravar(tagDTO);

    // Verificação

    final ArgumentCaptor<TagEntity> captorTagEntity = ArgumentCaptor.forClass(TagEntity.class);


    verify(repository, times(0)).findById(anyLong());
    verify(repository, times(1)).save(captorTagEntity.capture());

    assertEquals(titulo, captorTagEntity.getValue().getTitulo());
    assertNull(captorTagEntity.getValue().getId());
    assertEquals(11L, captorTagEntity.getValue().getIdJoomla());
    assertEquals("apelido", captorTagEntity.getValue().getApelido());
    assertEquals("uuid", captorTagEntity.getValue().getUuid());
    assertEquals(10L, tagDTOGravada.getId());

    assertThat(tagDTOGravada.getTitulo()).isEqualTo(titulo);
    assertThat(tagDTOGravada.getIdJoomla()).isEqualTo(10L);
    assertThat(tagDTOGravada.getApelido()).isEqualTo("apelido");
  }

  @Test
  void testGravarNotFound() throws Exception {

      // Preparação
      final String titulo = "Nova Tag";
      final TagDTO tagDTO = new TagDTO(10L, 11L, "uuid", titulo, "apelido");
      when(repository.findById(anyLong())).thenReturn(Optional.empty());

      when(repository.save(any(TagEntity.class)))
          .thenReturn(new TagEntity(10L, "uuid", 10L, titulo, "apelido", "language", null));



      // Ação
      final TagDTO tagDTOGravada = service.gravar(tagDTO);



      // Verificação

      final ArgumentCaptor<TagEntity> captorTagEntity = ArgumentCaptor.forClass(TagEntity.class);
      final ArgumentCaptor<Long> captorLong = ArgumentCaptor.forClass(Long.class);

      verify(repository, times(1)).findById(captorLong.capture());
      verify(repository, times(1)).save(captorTagEntity.capture());

      assertEquals(titulo, captorTagEntity.getValue().getTitulo());
      assertNull(captorTagEntity.getValue().getId());
      assertEquals(11L, captorTagEntity.getValue().getIdJoomla());
      assertEquals("apelido", captorTagEntity.getValue().getApelido());
      assertEquals("uuid", captorTagEntity.getValue().getUuid());
      assertEquals(10L, tagDTOGravada.getId());
      assertEquals(10L, captorLong.getValue());

      assertThat(tagDTOGravada.getTitulo()).isEqualTo(titulo);
      assertThat(tagDTOGravada.getIdJoomla()).isEqualTo(10L);
      assertThat(tagDTOGravada.getApelido()).isEqualTo("apelido");
  }
  
	  @BeforeEach
	  void setUp() {
	      tags = new ArrayList<>();
	      tags.add(mockTag("1", "Apelido1", "Titulo1", "Descricao1", "idioma1"));
	      tags.add(mockTag("2", "Apelido2", "Titulo2", "Descricao2", "idioma2"));
	      tags.add(mockTag("3", "Apelido3", "Titulo3", "Descricao3", "idioma3"));
	  }
	
	  @Test
	  void testAtualizarBanco() {
	      // Preparação
	      when(repository.buscarPorApelidoTitulo(any(), any())).thenAnswer(invocation -> {
	          String apelido = invocation.getArgument(0);
	          String titulo = invocation.getArgument(1);
	          return tags.stream().filter(tag -> tag.getApelido().equals(apelido) && tag.getTitulo().equals(titulo))
	                  .findFirst();
	      });
	
	      when(client.getTags()).thenReturn(getGenericoItemJoomlaResponse(0));
	      when(client.getTags(any(), any())).thenAnswer(invocation -> getGenericoItemJoomlaResponse(Integer.parseInt(invocation.getArgument(0))));
	
	      when(convert.convertJoomla(any())).thenAnswer(invocation -> {
	          AtributosTagJoomlaDTO attributes = invocation.getArgument(0);
	          return new TagEntity(0, attributes.getAlias(), attributes.getTitle(), attributes.getDescription(),
	                  attributes.getLanguage());
	      });
	
	      // Chama o método para teste
	      Map<String, Integer> result = service.atualizarBancoTag();
	
	      // Verifica o resultado
	      assertEquals(3, result.get("total"));
	      assertEquals(3, result.get("processados"));
	  }
	
	  private TagEntity mockTag(String id, String apelido, String titulo, String descricao, String idioma) {
	      return new TagEntity();
	  }
	
	  private GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getGenericoItemJoomlaResponse(int offset) {
	      List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> data = new ArrayList<>();
	      for (int i = offset; i < offset + 20 && i < tags.size(); i++) {
	          TagEntity tag = tags.get(i);
	          data.add(new GenericoJoomlaDataDTO<>(tag.getId(), tag.getApelido(), tag.getTitulo(), tag.getDescricao(),
	                  tag.getIdioma()));
	      }
	      PageRequest pageable = PageRequest.of(offset / 20, 20);
	      Page<TagEntity> page = new PageImpl<>(data, pageable, tags.size());
	      return new GenericoItemJoomlaResponse<>(page.getContent(), page.getLinks().getNext());
	  }
	}

}
