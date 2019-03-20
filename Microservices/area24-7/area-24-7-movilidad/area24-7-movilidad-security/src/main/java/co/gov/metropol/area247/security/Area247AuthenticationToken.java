package co.gov.metropol.area247.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;

public class Area247AuthenticationToken implements Authentication {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private String name;

	private final Object principal;

	private Object credentials;

	private Object details;

	private boolean authenticated = false;

	private final Collection<GrantedAuthority> authorities;

	private final Map<String, Object> permissions;

	public Area247AuthenticationToken(Object principal, Object credentials) {
		this(principal, credentials, AuthorityUtils.NO_AUTHORITIES, Collections.emptyMap());
		setAuthenticated(false);
	}

	public Area247AuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		this(principal, credentials, authorities, Collections.emptyMap());
	}

	public Area247AuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, Map<String, Object> permissions) {
		this.principal = principal;
		this.credentials = credentials;
		this.authorities = Collections.unmodifiableList(new ArrayList<>(authorities));
		this.permissions = Collections.unmodifiableMap(permissions);
		this.authenticated = true;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}
		this.authenticated = false;
	}

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}

	public void eraseCredentials() {
		this.credentials = null;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public Object getDetails() {
		return this.details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public boolean isAuthenticated() {
		return this.authenticated;
	}

	public Map<String, Object> getPermissions() {
		return permissions;
	}

}
