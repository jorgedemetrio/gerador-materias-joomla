/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author JorgeDemetrioPC
 * @since 27 de jun. de 2025 01:42:52
 * @version 1.0.27 de jun. de 2025
 */
public final class ControllerUtils {

    public static String getClientIpAddress(final HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public static String getClientIpProxyAddress(final HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
