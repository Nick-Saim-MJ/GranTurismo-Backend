package org.example.granturismo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
// import java.util.List; // Opcional

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleAuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (handler instanceof HandlerMethod handlerMethod) {
            PermitRoles permit = handlerMethod.getMethodAnnotation(PermitRoles.class);
            if (permit == null) {
                return true; // No se requiere permiso, continuar
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain;charset=" + StandardCharsets.UTF_8.name());
                response.getWriter().write("Error de autenticación: Se requiere iniciar sesión para acceder a este recurso.");
                return false;
            }

            String[] allowedRolesFromAnnotation = permit.value();
            Collection<? extends GrantedAuthority> userAuthorities = auth.getAuthorities();

            if (userAuthorities == null || userAuthorities.isEmpty()) {
                log.warn("Usuario autenticado {} sin roles asignados intentando acceder al método {}", auth.getName(), handlerMethod.getMethod().getName());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("text/plain;charset=" + StandardCharsets.UTF_8.name());
                response.getWriter().write("Acceso denegado: No tienes roles asignados.");
                return false;
            }

            boolean accessGranted = false;
            for (GrantedAuthority userAuthority : userAuthorities) {
                String userRole = userAuthority.getAuthority();
                if (userRole == null) continue;

                for (String allowedRole : allowedRolesFromAnnotation) {
                    if (userRole.equalsIgnoreCase(allowedRole.trim())) {
                        accessGranted = true;
                        break;
                    }
                }
                if (accessGranted) {
                    break;
                }
            }

            if (accessGranted) {
                log.debug("Acceso permitido para el usuario {} al método {}. Roles del usuario: {}", auth.getName(), handlerMethod.getMethod().getName(), userAuthorities.stream().map(GrantedAuthority::getAuthority).toList());
                return true;
            } else {
                log.warn("Acceso denegado para el usuario {} al método {} (Roles del usuario: {}). Roles requeridos por el método: {}",
                        auth.getName(),
                        handlerMethod.getMethod().getName(),
                        userAuthorities.stream().map(GrantedAuthority::getAuthority).toList(),
                        Arrays.toString(allowedRolesFromAnnotation));
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("text/plain;charset=" + StandardCharsets.UTF_8.name());
                response.getWriter().write("Acceso denegado: No tienes los permisos necesarios para realizar esta accion.");
                return false;
            }
        }
        return true;
    }
}