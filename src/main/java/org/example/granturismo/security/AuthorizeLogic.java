package org.example.granturismo.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

@Component
@Slf4j
public class AuthorizeLogic {

    public boolean hasAccess(HttpServletRequest request) {
        Object handlerAttr = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (!(handlerAttr instanceof HandlerMethod handler)) {
            log.warn("Handler no encontrado para esta request");
            return false;
        }

        Method method = handler.getMethod();
        PermitRoles annotation = method.getAnnotation(PermitRoles.class);
        if (annotation == null) {
            log.info("Método sin restricción de roles");
            return true; // si no tiene @PermitRoles, se permite
        }

        String[] allowedRoles = annotation.value();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) return false;

        for (GrantedAuthority authority : auth.getAuthorities()) {
            for (String allowedRole : allowedRoles) {
                if (authority.getAuthority().equalsIgnoreCase(allowedRole.trim())) {
                    return true;
                }
            }
        }

        return false;
    }
}