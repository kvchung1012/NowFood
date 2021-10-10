package com.cntt2.nowfood.config.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 10:30 AM
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        if (authentication.getPrincipal()=="anonymousUser"){
            return Optional.of("anonymousUser");
        }
        String username = ((UserPrincipal) authentication.getPrincipal()).getUsername();
        return Optional.of(username);
    }

}
