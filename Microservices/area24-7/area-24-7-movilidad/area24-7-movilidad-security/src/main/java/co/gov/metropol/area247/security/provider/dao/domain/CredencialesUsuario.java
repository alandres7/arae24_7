package co.gov.metropol.area247.security.provider.dao.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CredencialesUsuario extends User {

	private static final long serialVersionUID = 1L;

	private String nombre;

	private String correo;

	public CredencialesUsuario(String nombre, String username, String password, String correo, boolean enabled,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, true, true, true, authorities);
		this.nombre = nombre;
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public User setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getCorreo() {
		return correo;
	}

	public User setCorreo(String correo) {
		this.correo = correo;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Nombre: ").append(this.nombre).append("; ");
		sb.append("Correo: ").append(this.correo).append("; ");
		return sb.toString();
	}

}
