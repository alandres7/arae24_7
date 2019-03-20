package co.gov.metropol.area247.seguridad.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty
	@ApiModelProperty(notes = "Identificador único dentro de la base de datos de la aplicación")
	protected Long id;

	@JsonProperty
	@ApiModelProperty(notes = "Correo electrónico de la persona usuario ó administrador de la aplicación", required = true)
	@NotNull
	private String username;

	@JsonProperty
	@ApiModelProperty(notes = "Email del usuario que envia la peticion para su registro", required = true)
	private String email;

	@JsonProperty
	@ApiModelProperty(notes = "Celular de la persona que envia la solicitud de registro", required = false)
	private String celular;

	@Transient
	private Boolean terminosCondiciones;

	@JsonProperty
	@ApiModelProperty(notes = "Contraseña para el acceso del usuario a la aplicación", required = true)
	@NotNull
	private String contrasena;

	@JsonProperty
	@ApiModelProperty(notes = "Token entregado por el proveedor de notificaciones push que permite suscribir el dispositivo a la recepción de mensajes", required = false)
	private String tokenDispositivo;

	@JsonProperty
	@ApiModelProperty(notes = "Nombre de la persona usuario de la aplicación", required = false)
	private String nombre;

	@JsonProperty
	@ApiModelProperty(notes = "Apellido de la persona usuario de la aplicación", required = false)
	private String apellido;

	@JsonProperty
	@ApiModelProperty(notes = "Fecha de nacimiento de la persona usuario de la aplicación (en formato TIMESTAMP)", required = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaNacimiento;

	@ApiModelProperty(notes = "Describe la razón por la cual el usuario fué bloqueado para el uso de la aplicación", required = false)
	private String razonBloqueo;

	@ApiModelProperty(notes = "Describe la fecha en que el usuario fue bloqueado", required = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date fechaBloqueo;

	@ApiModelProperty(notes = "Describe las diferentes preferencias para el usuario", required = false)
	private String preferencias;

	@ApiModelProperty(notes = "Describe la fecha de la última actualización realizada", required = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date ultimaActualizacionPreferencias;

	@JsonProperty
	@ApiModelProperty(notes = "Define el id del rol del usuario con el que se establecen los permisos sobre la aplicación", required = false)
	private Long idRol;

	@JsonProperty
	@ApiModelProperty(notes = "Define el rol del usuario con el que se establecen los permisos sobre la aplicación", required = false)
	private String rolName;

	@JsonProperty
	@ApiModelProperty(notes = "Define la fuente de dónde se registró el usuario, sea una red social, otro sistema o desde la aplicación móvil del sistema Área 24/7", required = true)
	private String nombreFuenteRegistro;

	@JsonProperty
	@ApiModelProperty(notes = "Nombre del municipio de residencia del usuario de la aplicación", required = false)
	private String nombreMunicipio;

	@JsonProperty
	@ApiModelProperty(notes = "Género de la persona usuario de la aplicación, se permiten los valores F para femenino y M para masculino", required = false)
	private String nombreGenero;

	@JsonProperty
	@ApiModelProperty(notes = "Nivel educativo de la persona usuario de la aplicación, se permiten los valores Ninguno, Primaria, Secundaria, Profesional", required = false)
	private String nombreNivelEducativo;

	@JsonProperty
	@ApiModelProperty(notes = "Describe si el usuario se encuentra activo o si ha sido bloqueado", required = false)
	private boolean activo;

	@JsonProperty
	@ApiModelProperty(notes = "Rol asociado al usuario activo", required = false)
	private RolDto rol;
	
	@JsonProperty
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date ultimoIngreso;

	public UsuarioDto(String username, String email, String celular, String contrasena, String nombreFuenteRegistro,
			Boolean terminosCondiciones) {
		this.username = username;
		this.email = email;
		this.celular = celular;
		this.contrasena = contrasena;
		this.terminosCondiciones = terminosCondiciones;
		this.nombreFuenteRegistro = nombreFuenteRegistro;
	}

	public UsuarioDto(String username, String contrasena, String nombre, String apellido, String email,
			Date fechaNacimiento, String genero, String nivelEducativo, String municipio, String fuenteRegistro,
			String tokenDispositivo, String preferencias, Date ultimaActualizacionPreferencias) {
		this.username = username;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.nombreGenero = genero;
		this.nombreFuenteRegistro = fuenteRegistro;
		this.nombreNivelEducativo = nivelEducativo;
		this.nombreMunicipio = municipio;
		this.fechaNacimiento = fechaNacimiento;
		this.preferencias = preferencias;
		this.tokenDispositivo = tokenDispositivo;
		this.ultimaActualizacionPreferencias = ultimaActualizacionPreferencias;

	}

	public UsuarioDto(Long id, String username, String nombre, String apellido, String email, boolean activo,
			String genero, String fuenteRegistro, String nivelEducativo, String municipio, Date fechaBloqueo,
			String razonBloqueo) {
		this.id = id;
		this.username = username;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.activo = activo;
		this.nombreGenero = genero;
		this.nombreFuenteRegistro = fuenteRegistro;
		this.nombreNivelEducativo = nivelEducativo;
		this.nombreMunicipio = municipio;
		this.razonBloqueo = razonBloqueo;
		this.fechaBloqueo = fechaBloqueo;

	}

	public UsuarioDto(Long id, String username, String nombre, String apellido, String email, boolean activo,
			Long idRol, String rolName, String genero, String fuenteRegistro, String nivelEducativo, String municipio,
			Date fechaBloqueo, String razonBloqueo) {
		this.id = id;
		this.username = username;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.activo = activo;
		this.idRol = idRol;
		this.rolName = rolName;
		this.nombreGenero = genero;
		this.nombreFuenteRegistro = fuenteRegistro;
		this.nombreNivelEducativo = nivelEducativo;
		this.nombreMunicipio = municipio;
		this.razonBloqueo = razonBloqueo;
		this.fechaBloqueo = fechaBloqueo;
	}

	public UsuarioDto() {

	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getContrasena() {
		return contrasena;
	}

	public String getTokenDispositivo() {
		return tokenDispositivo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getRazonBloqueo() {
		return razonBloqueo;
	}

	public Date getFechaBloqueo() {
		return fechaBloqueo;
	}

	public void setFechaBloqueo(Date fechaBloqueo) {
		this.fechaBloqueo = fechaBloqueo;
	}

	public String getPreferencias() {
		return preferencias;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public void setTokenDispositivo(String tokenDispositivo) {
		this.tokenDispositivo = tokenDispositivo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setRazonBloqueo(String razonBloqueo) {
		this.razonBloqueo = razonBloqueo;
	}

	public void setPreferencias(String preferencias) {
		this.preferencias = preferencias;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Date getUltimaActualizacionPreferencias() {
		return ultimaActualizacionPreferencias;
	}

	public void setUltimaActualizacionPreferencias(Date ultimaActualizacionPreferencias) {
		this.ultimaActualizacionPreferencias = ultimaActualizacionPreferencias;
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

	public RolDto getRol() {
		return rol;
	}

	public void setRol(RolDto rol) {
		this.rol = rol;
	}

	public String getNombreFuenteRegistro() {
		return nombreFuenteRegistro;
	}

	public void setNombreFuenteRegistro(String nombreFuenteRegistro) {
		this.nombreFuenteRegistro = nombreFuenteRegistro;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public String getNombreGenero() {
		return nombreGenero;
	}

	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}

	public String getNombreNivelEducativo() {
		return nombreNivelEducativo;
	}

	public void setNombreNivelEducativo(String nombreNivelEducativo) {
		this.nombreNivelEducativo = nombreNivelEducativo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getTerminosCondiciones() {
		return terminosCondiciones;
	}

	public void setTerminosCondiciones(Boolean terminosCondiciones) {
		this.terminosCondiciones = terminosCondiciones;
	}

	public Date getUltimoIngreso() {
		return ultimoIngreso;
	}

	public void setUltimoIngreso(Date ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}
	
	
}
