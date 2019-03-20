package co.gov.metropol.area247.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class UsuarioDTO extends AbstractDTO{
	
	@NotNull
	@Size(max = 200)
	@Loggable
	private String nombre;
	
	@NotNull
	@Size(max = 80)
	@Loggable
	private String username;
	
	@NotNull
	@Size(max = 80)
	private String password;
	
	@NotNull
	@Size(max = 180)
	@Loggable
	private String correo;
	
	@Loggable
	private boolean cuentaNoExpirada;
	
	@Loggable
	private boolean cuentaNoBloqueada;
	
	@Loggable
	private boolean credencialesNoExpiradas;
	
	@Loggable
	private Date ultimoIngreso;
	
	@Override
	public UsuarioDTO withId(Long id) {
		super.setId(id);
		return this;
	}
	
	@Override
	public UsuarioDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isCuentaNoExpirada() {
		return cuentaNoExpirada;
	}

	public void setCuentaNoExpirada(boolean cuentaNoExpirada) {
		this.cuentaNoExpirada = cuentaNoExpirada;
	}

	public boolean isCuentaNoBloqueada() {
		return cuentaNoBloqueada;
	}

	public void setCuentaNoBloqueada(boolean cuentaNoBloqueada) {
		this.cuentaNoBloqueada = cuentaNoBloqueada;
	}

	public boolean isCredencialesNoExpiradas() {
		return credencialesNoExpiradas;
	}

	public void setCredencialesNoExpiradas(boolean credencialesNoExpiradas) {
		this.credencialesNoExpiradas = credencialesNoExpiradas;
	}

	public Date getUltimoIngreso() {
		return ultimoIngreso;
	}

	public void setUltimoIngreso(Date ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}
}
