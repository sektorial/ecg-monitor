package ua.com.ivolnov.ecg.configuration.authentication;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.com.ivolnov.ecg.common.UserRole;

@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) {
        for (final var authority : authentication.getAuthorities()) {
            try {
                if (authority.getAuthority().equals(UserRole.ROLE_ADMIN)) {
                    response.sendRedirect("/web/admin");
                } else {
                    response.sendRedirect("/web/staff");
                }
            } catch (final IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

}
