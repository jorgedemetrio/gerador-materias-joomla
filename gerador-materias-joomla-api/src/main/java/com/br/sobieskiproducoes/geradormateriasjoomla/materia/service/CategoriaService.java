/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.dto.RetornoBusinessDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.CategoriaJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.joomla.AtributosCategoriaJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.CategoriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.CategoriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.CategoriaConvert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:06
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class CategoriaService {

  private final CategoriaRepository categoriaRepository;
  private final CategoriaJoomlaClient categoriaJoomlaClient;
  private final CategoriaConvert convert;
  private final ConfiguracoesProperties properties;

  public Boolean apagar(@NotNull final Long id) {



    final Optional<CategoriaEntity> categoriaEntityOpt = categoriaRepository.findById(id);
    if (!categoriaEntityOpt.isPresent()) {
      return Boolean.FALSE;
    }
    categoriaRepository.delete(categoriaEntityOpt.get());
    log.info("Apagado a categoria.".concat(id.toString()));
    return Boolean.TRUE;
  }

  @Transactional
  public Map<String, Integer> atualizarBancoCategoria() {
    final List<CategoriaEntity> itens = new ArrayList<>();
    GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>> consulta = null;
    int offset = 0;
    final Map<String, Integer> retorno = new HashMap<>();
    // Busca as Categorias
    do {
      if (isNull(consulta)) {
        // Primeira pagina
        consulta = categoriaJoomlaClient.getCategorias();
      } else {
        // Faz a proxima busca, pegando o next page e removendo a URL
        consulta = categoriaJoomlaClient.getCategorias(String.valueOf(offset * 20), "20");

      }
      offset++;
      if (nonNull(consulta) && nonNull(consulta.getData())) {
        itens.addAll(consulta.getData().stream()
            .filter(c -> nonNull(c.getAttributes().getPublished()) && c.getAttributes().getPublished().intValue() > 0
            // Só importa a categoria que pertence ao Idioma permitido.
                && (isNull(c.getAttributes())
                    || c.getAttributes().getLanguage().equals(properties.getJoomla().getIdioma())
                    || MateriaConstants.SEM_IDIOMA.equals(c.getAttributes().getLanguage())))
            .map(convert::convert).toList());

      }

    } while (nonNull(consulta) && nonNull(consulta.getLinks()) && nonNull(consulta.getLinks().getNext())
        && nonNull(consulta.getData()) && !consulta.getLinks().getNext().isBlank());
    retorno.put("total", itens.size());
    // Ordena pelo Id do Joomla
    itens.sort((o1, o2) -> (int) (o1.getIdJoomla().longValue() - o2.getIdJoomla().longValue()));

    // Pega todos as categorias principais.
    List<CategoriaEntity> itensSalvar = itens.stream()
        .filter(c -> isNull(c.getPai()) || isNull(c.getPai().getIdJoomla()) || c.getPai().getIdJoomla().equals(0L))
        .toList();

    // Se não achar nada que não tenha pai, ele pega o com id menor tira o pai dele
    // e manda para gravação.
    if (itensSalvar.isEmpty() && !itens.isEmpty()) {
      // Pega o meno Id que seria o primeiro devido a ordenação
      final Long idPai = itens.get(0).getPai().getIdJoomla();
      // procura no banco o pai dele.
      final Optional<CategoriaEntity> itemPai = categoriaRepository.findByIdJoomla(idPai);
      final CategoriaEntity pai = itemPai.isPresent() ? itemPai.get() : null;
      itensSalvar = itens.stream()
          .filter(
              c -> nonNull(c.getPai()) && nonNull(c.getPai().getIdJoomla()) && idPai.equals(c.getPai().getIdJoomla()))
          .toList();

      itensSalvar.forEach(c -> c.setPai(pai));

    }
    int processados = 0;
    log.info("Gravando lista de %d de categorias.".formatted(itens.size()));
    List<CategoriaEntity> agravar = null;
    while (nonNull(itensSalvar) && !itensSalvar.isEmpty()) {

      agravar = itensSalvar.stream().filter(this::jaEstaSalvoCorrigePai).toList();
      processados += agravar.size();
      agravar.forEach(categoriaRepository::save);

      itens.removeAll(itensSalvar);

      // Copia os valores para um variavel temporaria para fazer o filtro.
      final List<CategoriaEntity> itensSalvados = itensSalvar;

      // Busca os filhos dos paissalvos, proximos a serem salvos.
      itensSalvar = itens.stream().filter(c -> nonNull(c.getPai()) && nonNull(c.getPai().getIdJoomla()))
          .filter(c1 -> (itensSalvados.stream().filter(c2 -> c2.getIdJoomla().equals(c1.getPai().getIdJoomla()))
              .toList().size() > 0))
          .toList();

    }
    retorno.put("processados", processados);
    return retorno;
  }

  public RetornoBusinessDTO<CategoriaDTO> busca(final String titulo, final Integer pagina) {

    Page<CategoriaEntity> itemPagina = null;

    if (nonNull(titulo) && !titulo.isBlank()) {
      itemPagina = categoriaRepository.buscaPorTitulo(titulo.trim().toUpperCase(), PageRequest
          .of(nonNull(pagina) ? pagina : 0, MateriaConstants.MAX_INTENS_PER_PAGE, Sort.by("titulo").ascending()));
    } else {
      itemPagina = categoriaRepository.findAll(PageRequest.of(nonNull(pagina) ? pagina : 0,
          MateriaConstants.MAX_INTENS_PER_PAGE, Sort.by("titulo").ascending()));
    }

    final List<CategoriaDTO> retorno = itemPagina.get().map(convert::convertCategoriaDTO).toList();

    return new RetornoBusinessDTO<>(itemPagina.getTotalElements(), itemPagina.getTotalPages(), retorno);

  }

  /**
   *
   * @return O univero de registros atualizados.
   */

  public CategoriaDTO findById(final Long id) {
    final Optional<CategoriaEntity> entity = categoriaRepository.findById(id);

    if (!entity.isPresent()) {
      return null;
    }

    return convert.convert(entity.get());
  }

  public CategoriaDTO gravar(@NotNull @Valid final CategoriaDTO categoria) {
    CategoriaEntity categoriaEntity = null;

    if (nonNull(categoria.getId()) && categoria.getId() > 0) {
      final Optional<CategoriaEntity> categoriaEntityOpt = categoriaRepository.findById(categoria.getId());

      if (categoriaEntityOpt.isPresent()) {
        categoriaEntity = categoriaEntityOpt.get();
        log.info("Fez merge da categoria id: " + categoria.getId().toString());
        convert.merge(categoria, categoriaEntity);
      }
    }
    if (isNull(categoriaEntity)) {
      log.info("Novo registro");
      categoriaEntity = convert.toEntityNova(categoria);
    }

    return convert.convert(categoriaRepository.save(categoriaEntity));
  }

  private boolean jaEstaSalvoCorrigePai(final CategoriaEntity c1) {
    final boolean naoEstaSalvo = !categoriaRepository.findByIdJoomla(c1.getIdJoomla()).isPresent();
    // Se não estiver salvo verifica se o pai já está no banco , se não achar deixa
    // null
    if (naoEstaSalvo) {
      if (nonNull(c1.getPai()) && nonNull(c1.getPai().getIdJoomla()) && isNull(c1.getPai().getId())
          && !c1.getPai().getIdJoomla().equals(0L)) {
        final Optional<CategoriaEntity> item = categoriaRepository.findByIdJoomla(c1.getPai().getIdJoomla());
        if (item.isPresent()) {
          c1.setPai(item.get());
        } else {
          c1.setPai(null);
        }
      }
      if (nonNull(c1.getPai()) && isNull(c1.getPai().getId())) {
        c1.setPai(null);
      }
    }
    return naoEstaSalvo;
  }
}
