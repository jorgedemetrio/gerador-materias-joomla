/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.interceptor;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author JorgeDemetrioPC
 * @since 2 de jul. de 2025 01:12:27
 * @version 1.0.2 de jul. de 2025
 */
public class TrackingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        MDC.put("trackingId", request.getHeader("X-Tracking-ID"));
        MDC.put("sessionId", request.getHeader("X-Session-ID"));
        MDC.put("pathUrl", request.getRequestURI());

        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex)
            throws Exception {
        MDC.put("trackingId", null);
        MDC.put("sessionId", null);
        MDC.put("pathUrl", null);

    }
}
