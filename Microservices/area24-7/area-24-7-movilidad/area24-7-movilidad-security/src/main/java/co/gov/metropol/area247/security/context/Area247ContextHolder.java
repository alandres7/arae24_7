package co.gov.metropol.area247.security.context;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import co.gov.metropol.area247.security.Area247AuthenticationToken;
import co.gov.metropol.area247.security.provider.dao.domain.enums.Roles;

public class Area247ContextHolder {

    public static boolean isAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && authentication instanceof Area247AuthenticationToken) {
            return authentication.isAuthenticated();
        }
        return false;
    }

    public static Area247AuthenticationToken getAuthentication() {
        if (isAuthenticate()) {
            return (Area247AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        }
        return null;
    }


    public static boolean isSuperAdmin() {
        Area247AuthenticationToken authentication = getAuthentication();
        if (authentication != null) {
            return hasRole(authentication.getAuthorities(), Roles.ROLE_SUPER_ADMIN);
        }
        return false;
    }

    public static boolean isAdmin() {
        Area247AuthenticationToken authentication = getAuthentication();
        if (authentication != null) {
            return hasRole(authentication.getAuthorities(), Roles.ROLE_ADMIN);
        }
        return false;
    }

    public static boolean hasRole(Collection<? extends GrantedAuthority> authorities, Roles role) {
        for (GrantedAuthority grantedAuthority : authorities) {
            if (role.toString().equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
