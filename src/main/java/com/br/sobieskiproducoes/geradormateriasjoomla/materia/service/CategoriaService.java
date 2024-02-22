/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
  public int atualizarBancoCategoria() {
    final List<CategoriaEntity> itens = new ArrayList<>();
    ItemResponse<CategoriaJoomlaDTO> consulta = null;
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
//        itens.addAll(consulta.getData().stream()
//            .filter(c -> nonNull(c.getAttributes().getPublished()) && c.getAttributes().getPublished().intValue() > 0)
//            .map(convert::convert).collect(Collectors.toList()));

        itens.addAll(consulta.getData().stream().map(convert::convert).collect(Collectors.toList()));
      }

    } while (nonNull(consulta) && nonNull(consulta.getLinks()) && nonNull(consulta.getLinks().getNext())
        && nonNull(consulta.getData()) && !consulta.getLinks().getNext().isBlank());

    // Pega todos as categorias principais.
    List<CategoriaEntity> itensSalvar = itens.stream()
        .filter(c -> isNull(c.getPai()) || isNull(c.getPai().getIdJoomla())).collect(Collectors.toList());

    final int total = itens.size();
    while (nonNull(itensSalvar) && !itensSalvar.isEmpty()) {
      itensSalvar.stream().filter(c1 -> !categoriaRepository.findByIdJoomla(c1.getIdJoomla()).isPresent())
          .forEach(categoriaRepository::save);

      itens.removeAll(itensSalvar);

      // Copia os valores para um variavel temporaria para fazer o filtro.
      final List<CategoriaEntity> itensSalvados = itensSalvar;

      // Busca os proximos a serem salvos.
      itensSalvar = itens.stream().filter(c -> nonNull(c.getPai()) && nonNull(c.getPai().getIdJoomla()))
          .filter(c1 -> (itensSalvados.stream().filter(c2 -> {
            log.info("c2.getIdJoomla() : ".concat(c2.getIdJoomla().toString()));
            log.info("c1.getPai().getIdJoomla() : ".concat(c1.getPai().getIdJoomla().toString()));
            return c2.getIdJoomla().equals(c1.getPai().getIdJoomla());
          }).collect(Collectors.toList()).size() > 0)).collect(Collectors.toList());

    }

    return total;
  }

}
