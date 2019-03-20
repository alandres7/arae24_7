package co.gov.metropol.area247.centrocontrol.dto;

import java.util.Date;
/**
 * 
 * @author ageofuentes
 * <h3>
 * Dto asociado al entity LoginExterno
 * </h3>
 *
 */
public class LoginExternoDto {
	private Long id;
	private String username;
	private String token;
	private String nombre;
	private String descripcion;
	private Date fechaAcceso;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFechaAcceso() {
		return fechaAcceso;
	}
	public void setFechaAcceso(Date fechaAcceso) {
		this.fechaAcceso = fechaAcceso;
	}
	public LoginExternoDto() {
	}
	
}
