/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.ItemResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.CategoriaJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.CategoriaJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.CategoriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.CategoriaConvert;

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

  /**
   *
   * @return O univero de registros atualizados.
   */
  @Transactional
  public int atualizarBancoCategoria() {
    final List<CategoriaEntity> itens = new ArrayList<>();
    ItemResponse<List<CategoriaJoomlaDTO>> consulta = null;
    int offset = 0;

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
            .filter(c -> nonNull(c.getAttributes().getPublished()) && c.getAttributes().getPublished().intValue() > 0)
            .map(convert::convert).collect(Collectors.toList()));

      }

    } while (nonNull(consulta) && nonNull(consulta.getLinks()) && nonNull(consulta.getLinks().getNext())
        && nonNull(consulta.getData()) && !consulta.getLinks().getNext().isBlank());
    final int total = itens.size();

    // Ordena pelo Id do Joomla
    itens.sort((o1, o2) -> Integer.valueOf((int) ((o1.getIdJoomla().longValue() - o2.getIdJoomla().longValue()))));

    // Pega todos as categorias principais.
    List<CategoriaEntity> itensSalvar = itens.stream()
        .filter(c -> isNull(c.getPai()) || isNull(c.getPai().getIdJoomla()) || c.getPai().getIdJoomla().equals(0L))
        .collect(Collectors.toList());

    // Se não achar nada que não tenha pai, ele pega o com id menor tira o pai dele
    // e manda para gravação.
    if (itensSalvar.isEmpty() && !itens.isEmpty()) {
      // Pega o meno Idque seria o primeiro devido a ordenação
      final Long idPai = itens.get(0).getPai().getIdJoomla();
      // procura no banco o pai dele.
      final Optional<CategoriaEntity> itemPai = categoriaRepository.findByIdJoomla(idPai);
      final CategoriaEntity pai = itemPai.isPresent() ? itemPai.get() : null;
      itensSalvar = itens.stream()
          .filter(
              c -> nonNull(c.getPai()) && nonNull(c.getPai().getIdJoomla()) && idPai.equals(c.getPai().getIdJoomla()))
          .collect(Collectors.toList());

      itensSalvar.forEach(c -> c.setPai(pai));

    }
    log.info("Gravando lista de %d de categorias.".formatted(total));
    while (nonNull(itensSalvar) && !itensSalvar.isEmpty()) {

      itensSalvar.stream().filter(this::jaEstaSalvoCorrigePai).forEach(categoriaRepository::save);

      itens.removeAll(itensSalvar);

      // Copia os valores para um variavel temporaria para fazer o filtro.
      final List<CategoriaEntity> itensSalvados = itensSalvar;

      // Busca os filhos dos paissalvos, proximos a serem salvos.
      itensSalvar = itens.stream().filter(c -> nonNull(c.getPai()) && nonNull(c.getPai().getIdJoomla()))
          .filter(c1 -> (itensSalvados.stream().filter(c2 -> c2.getIdJoomla().equals(c1.getPai().getIdJoomla()))
              .collect(Collectors.toList()).size() > 0))
          .collect(Collectors.toList());

    }

    return total;
  }

  private boolean jaEstaSalvoCorrigePai(final CategoriaEntity c1) {
    final boolean naoEstaSalvo = !categoriaRepository.findByIdJoomla(c1.getIdJoomla()).isPresent();
    // Sen ão estiver salvo verifica se o pai já está no banco , sen ão achar deixa
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
