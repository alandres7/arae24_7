package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/** Clase encargada de almacenar los diversos usuarios que usara el centro de control */


@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario implements Serializable{

    private Long id;

    private String username;

    private String contrasena;

    private String nombre;

    private String apellido;  

    @DateTimeFormat(pattern = "dd-MMM-YYYY")
    private Date fechaNacimiento; 

    private String nickname; 

    @DateTimeFormat(pattern = "dd-MMM-YYYY")
    private Date ultimaActualizacionPreferencias;

    private Boolean activo;
    
    private Long idRol;
    
    private String rolName;
    
    private Rol rol;
   

	

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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getUltimaActualizacionPreferencias() {
		return ultimaActualizacionPreferencias;
	}

	public void setUltimaActualizacionPreferencias(Date ultimaActualizacionPreferencias) {
		this.ultimaActualizacionPreferencias = ultimaActualizacionPreferencias;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getRolName() {
		return rolName;
	}

	public void setRolName(String rolName) {
		this.rolName = rolName;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
						    
}