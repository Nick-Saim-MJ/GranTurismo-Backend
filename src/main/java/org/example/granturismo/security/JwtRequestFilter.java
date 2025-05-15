package org.example.granturismo.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger; // Recomendado para logging
import org.slf4j.LoggerFactory; // Recomendado para logging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils; // Para verificar Strings de forma segura
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor // Asegúrate de que jwtTokenUtil y jwtUserDetailsService sean 'final' para que Lombok los incluya
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtTokenUtil jwtTokenUtil; // Preferir constructor injection (marcar como final)
    private final JwtUserDetailsService jwtUserDetailsService; // Ya es final y manejado por @RequiredArgsConstructor

    // private final AuthorizeLogic authorizeLogic; // Comentado, la autorización de roles se maneja mejor en RoleAuthorizationInterceptor

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // No es estrictamente necesario limpiar el contexto aquí al inicio de cada request si Spring Security lo maneja bien,
        // pero es crucial hacerlo si una autenticación falla dentro de este filtro antes de proceder.
        // SecurityContextHolder.clearContext(); // Considera su necesidad o moverlo a bloques catch específicos.

        try {
            String jwtToken = extractJwtFromRequest(request);

            if (StringUtils.hasText(jwtToken)) {
                String username = jwtTokenUtil.getUsernameFromToken(jwtToken); // Puede lanzar excepciones JWT

                // Solo configurar la autenticación si no existe una ya y el username es válido
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username); // Puede lanzar UsernameNotFoundException

                    if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.debug("Usuario '{}' autenticado correctamente.", username);
                    } else {
                        // Token válido en formato pero inválido en contenido/firma contra UserDetails
                        logger.warn("Validación de token fallida para el usuario: {}", username);
                        SecurityContextHolder.clearContext();
                        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Error de autenticación: Token inválido (falló la validación detallada).");
                        return; // No continuar con la cadena de filtros
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            logger.warn("El token JWT ha expirado: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Error de autenticación: El token ha expirado.");
            return; // No continuar
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            logger.warn("Token JWT inválido o malformado: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Error de autenticación: El token es inválido o está malformado.");
            return; // No continuar
        } catch (UsernameNotFoundException e) {
            logger.warn("Usuario no encontrado para el token JWT: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Error de autenticación: Usuario no encontrado.");
            return; // No continuar
        } catch (Exception e) {
            // Captura general para cualquier otra excepción inesperada durante el procesamiento del token
            logger.error("Error inesperado durante el procesamiento del token JWT.", e);
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor al procesar el token.");
            return; // No continuar
        }

        // Continuar con la cadena de filtros (si no se retornó antes por un error)
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer ".length() == 7
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("text/plain;charset=" + StandardCharsets.UTF_8.name());
        response.getWriter().write(message);
    }
}