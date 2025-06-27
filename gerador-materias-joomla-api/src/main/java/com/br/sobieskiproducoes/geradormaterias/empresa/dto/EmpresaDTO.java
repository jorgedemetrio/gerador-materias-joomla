/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.dto;

import java.util.List;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 13 de jun. de 2025 19:43:13
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {

    private String id;

    private String nome;

    private Boolean principal;

    private ConfiguracoesDTO configuracao;

    private List<UsuarioSistemaDTO> usuarios;

}
