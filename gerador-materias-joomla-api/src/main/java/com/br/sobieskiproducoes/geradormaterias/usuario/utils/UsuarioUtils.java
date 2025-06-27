/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.br.sobieskiproducoes.geradormaterias.empresa.model.EmpresaEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 00:17:41
 * @version 1.0.27 de jun. de 2025
 */
@Log
public class UsuarioUtils {
    public static String empresaPrincipalId(final UsuarioEntity usuario) {
        try {
            if (isNull(usuario) || isNull(usuario.getEmpresas()) || usuario.getEmpresas().isEmpty()) {
                return null;
            }

            final List<String> ids = usuario.getEmpresas().stream().filter(n -> nonNull(n.getPrincipal()) && n.getPrincipal().booleanValue())
                    .map(EmpresaEntity::getId).collect(Collectors.toList());

            if (nonNull(ids) && ids.size() > 0) {
                return ids.get(0);
            }
            return usuario.getEmpresas().get(0).getId();
        } catch (final org.hibernate.LazyInitializationException tr) {
            log.log(Level.SEVERE, String.format("[ERRO] UsuarioUtils#getEmpresaId (LazyInitializationException) error: %s (usuario: %s ) ",
                    tr.getLocalizedMessage(), usuario.getId()), tr.getCause());
        } catch (final Exception tr) {
            log.log(Level.SEVERE, String.format("[ERRO] UsuarioUtils#getEmpresaId error: %s (usuario: %s ) ", tr.getLocalizedMessage(), usuario.getId()),
                    tr.getCause());
            throw tr;
        }
        return null;
    }
}
