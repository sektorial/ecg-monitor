package ua.com.ivolnov.ecg.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserRoleAdvice {

    @ModelAttribute("userRole")
    public String userRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getAuthorities().stream()
                    .map(granted -> granted.getAuthority().replace("ROLE_", ""))
                    .findFirst()
                    .orElse("");
        }
        return "";
    }
}
