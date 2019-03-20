package co.gov.metropol.area247.centrocontrol.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class CentroControlAuthenticationToken implements Authentication {

	private static final long serialVersionUID = -3993460953154788098L;

	private String name;

	private final Object principal;

	private Object credentials;

	private Object details;

	private boolean authenticated = false;

	private final Collection<GrantedAuthority> authorities;

	public CentroControlAuthenticationToken(Object principal, Object credentials) {
		this.principal = principal;
		this.credentials = credentials;
		this.authorities = AuthorityUtils.NO_AUTHORITIES;

		setAuthenticated(false);
	}

	public CentroControlAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		this.principal = principal;
		this.credentials = credentials;
		this.authorities = Collections.unmodifiableList(new ArrayList<GrantedAuthority>(authorities));
		this.authenticated = true;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getDetails() {
		return details;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}
		this.authenticated = false;
	}

}
