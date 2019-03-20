package co.gov.metropol.area247.security.support.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityAuditorAware")
public class SecurityAuditorSupport implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && authentication.isAuthenticated())
            return (String) authentication.getPrincipal();
        return null;
    }
}
