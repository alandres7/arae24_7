package co.gov.metropol.area247.security.service.impl;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.security.Area247AuthenticationToken;
import co.gov.metropol.area247.security.service.ISecurityService;

@Service
public class SecurityServiceImpl implements ISecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public boolean authenticate(String username, String password) throws AuthenticationException {
        Area247AuthenticationToken newAuthentication = (Area247AuthenticationToken) authenticationManager.authenticate(new Area247AuthenticationToken(username, password));
        if ((newAuthentication != null) && (newAuthentication.isAuthenticated())) {
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            return true;
        }
        return false;
    }
}
