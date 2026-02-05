package com.frain.frainapi.shared.infrastructure.security;

import com.frain.frainapi.shared.domain.exceptions.JwtIncompleteException;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;
import com.frain.frainapi.shared.domain.model.valueobjects.User;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class UserContext {

    private Object getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!isAuthenticated()) {
            log.warn("No authenticated user found in security context");
            throw new RuntimeException("No authenticated user found");
        }

        return authentication.getPrincipal();
    }
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public UserId getCurrentUserId() {
        Object principal = getPrincipal();

        if (principal instanceof Jwt jwt) {
            return new UserId(UUID.fromString(jwt.getSubject()));
        }

        throw  new JwtIncompleteException("Claim 'sub' is missing in the JWT token");
    }

    public UserName getCurrentUserName() {
        Object principal = getPrincipal();

        if (principal instanceof Jwt jwt) {
            String username = jwt.getClaimAsString("username");
            if (username != null) {
                return new UserName(username);
            } else {
                throw new JwtIncompleteException("Claim 'username' is missing in the JWT token");
            }
        }

        throw  new JwtIncompleteException("Claim 'username' is missing in the JWT token");
    }

    public EmailAddress getCurrentUserEmail() {
        Object principal = getPrincipal();

        if (principal instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");
            if (email != null) {
                return new EmailAddress(email);
            } else {
                throw new JwtIncompleteException("Claim 'email' is missing in the JWT token");
            }
        }

        throw  new JwtIncompleteException("Claim 'email' is missing in the JWT token");
    }

    public User getCurrentUser() {
        return new User(
                getCurrentUserId(),
                getCurrentUserName(),
                getCurrentUserEmail()
        );
    }
}
