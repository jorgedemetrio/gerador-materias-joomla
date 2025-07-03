/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.domain;

import com.br.sobieskiproducoes.geradormaterias.domain.AbstractObservabilidadeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 01:27:41
 * @version 1.0.27 de jun. de 2025
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_termos_empresa")
public class TermosEmpresaEntity extends AbstractObservabilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 40)
    private String id;

    @NotBlank
    @Size(min = 10, max = 250)
    @Column(name = "nome", insertable = true, updatable = true, nullable = true, unique = false, length = 250)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", insertable = true, updatable = true, nullable = false, unique = false)
    private EmpresaEntity empresa;

}
