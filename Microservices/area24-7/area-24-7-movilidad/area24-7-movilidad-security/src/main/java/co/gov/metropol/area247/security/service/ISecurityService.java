package co.gov.metropol.area247.security.service;

import javax.naming.AuthenticationException;

public interface ISecurityService {

    boolean authenticate(String username, String password) throws AuthenticationException;
}
