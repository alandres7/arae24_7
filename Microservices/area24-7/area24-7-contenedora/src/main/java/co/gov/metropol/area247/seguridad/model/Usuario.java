package co.gov.metropol.area247.seguridad.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "T247SEG_USUARIO", schema = "CONTENEDOR")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_USUARIO_ID", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_GEN")  
	private Long id;
	
	@JsonProperty
	@ApiModelProperty(notes = "Correo electrónico de la persona usuario ó administrador de la aplicación", required = true)
	@NotNull
	@Size(max = 100)
	@Column(name = "EMAIL", unique = true)
	private String email;
	
	@Size(max = 20)
	@Column(name = "CELULAR")
	private String celular;
	
	@Column(name = "TERMINOS")
	private boolean terminosCondiciones;

	@JsonProperty
	@ApiModelProperty(notes = "Contraseña para el acceso del usuario a la aplicación", required = true)
	@NotNull
	@Size(max = 100)
	@Column(name = "CONTRASENA")
	private String contrasena;

	@JsonProperty
	@ApiModelProperty(notes = "Token entregado por el proveedor de notificaciones push que permite suscribir el dispositivo a la recepción de mensajes", required = false)
	@Column(name = "S_TOKEN_DISPOSITIVO")
	private String tokenDispositivo;

	@JsonProperty
	@ApiModelProperty(notes = "Nombre de la persona usuario de la aplicación", required = false)
	@Column(name = "NOMBRES")
	@Size(max = 200)
	private String nombre;

	@JsonProperty
	@ApiModelProperty(notes = "Apellido de la persona usuario de la aplicación", required = false)
	@Column(name = "APELLIDOS")
	@Size(max = 200)
	private String apellido;

	@JsonProperty
	@ApiModelProperty(notes = "Fecha de nacimiento de la persona usuario de la aplicación (en formato TIMESTAMP)", required = false)
	@Column(name = "FECHA_NACIMIENTO")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaNacimiento;

	@JsonProperty
	@ApiModelProperty(notes = "Apodo o nombre personalizado para la persona usuario de la aplicación, es un nombre único", required = false)
	@Column(name = "USERNAME")
	@Size(max = 100)
	private String username;

	@ApiModelProperty(notes = "Fecha del último ingreso de la persona usuario o administradora de la aplicación, se calcula automaticamente tras login", required = false)
	@Column(name = "D_ULTIMO_INGRESO")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date ultimoIngreso;

	@JsonProperty
	@ApiModelProperty(notes = "Describe si el usuario se encuentra activo o si ha sido bloqueado", required = false)
	@NotNull
	@Column(name = "ESTADO_USUARIO")
	private boolean activo;

	@JsonIgnore
	@ApiModelProperty(notes = "Describe la razón por la cual el usuario fué bloqueado para el uso de la aplicación", required = false)
	@Column(name = "S_RAZON_BLOQUEO")
	@Size(max = 2000)
	private String razonBloqueo;

	@JsonIgnore
	@ApiModelProperty(notes = "Describe la fecha en que el usuario fue bloqueado", required = false)
	@Column(name = "D_BLOQUEO")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date fechaBloqueo;

	@ApiModelProperty(notes = "Describe las diferentes preferencias para el usuario", required = false)
	@Column(name = "PREFERENCIAS", columnDefinition="TEXT")
	private String preferencias;
	
	@ApiModelProperty(notes = "Describe la fecha de la última actualización realizada", required = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "D_ULTIMA_ACTUALIZACION")
	private Date ultimaActualizacionPreferencias;	
	
	@JsonProperty
	@ApiModelProperty(notes = "Nombre del rol que tiene el usuario", required = false)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_ROL")
	private Rol rol;
	
	@JsonProperty
	@ApiModelProperty(notes = "Nombre del municipio de residencia del usuario de la aplicación", required = false)
	@ManyToOne
	@JoinColumn(name = "ID_MUNICIPIO")
	private Municipio municipio;
	
	@JsonProperty
	@ApiModelProperty(notes = "Género de la persona usuario de la aplicación, se permiten los valores F para femenino y M para masculino", required = false)
	@ManyToOne
	@JoinColumn(name = "ID_GENERO")
	private Genero genero;
	
	@JsonProperty
	@ApiModelProperty(notes = "Nivel educativo de la persona usuario de la aplicación, se permiten los valores Ninguno, Primaria, Secundaria, Profesional", required = false)
	@ManyToOne
	@JoinColumn(name = "ID_NIVEL_EDUCATIVO")
	private NivelEducativo nivelEducativo;
	
	
	@ApiModelProperty(notes = "Describe por qué medio el usuario o administrador ha realizado el registro a la aplicación, los posibles valores son a nivel de usuario de la aplicación: FB para Facebook, GP para Google Plus, AP para registro desde la aplicación 24/7. A nivel de administrador AD para Directorio Activo", required = false)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_FUENTE_REGISTRO")
	private FuenteRegistro fuenteRegistro;
	

	@JsonIgnore
	@ApiModelProperty(notes = "Describe al estado de otro lugar de residencia del usuario", required = false)
	@Column(name = "S_ESTADO_EXTRANJERO")
	@Size(max = 200)
	private String estadoExtranjero;
	
	@JsonIgnore
	@ApiModelProperty(notes = "Describe la ciudad de otro lugar de residencia del usuario", required = false)
	@Column(name = "S_CIUDAD_EXTRANJERA")
	@Size(max = 200)
	private String ciudadExtranjera;
	
	@JsonIgnore
	@ApiModelProperty(notes = "Describe otro lugar de residencia del usuario", required = false)
	@Column(name = "S_PAIS")
	@Size(max = 200)
	private String otroPais;

	public Usuario()
	{
		super();
	}
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getUltimoIngreso() {
		return ultimoIngreso;
	}

	public void setUltimoIngreso(Date ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getRazonBloqueo() {
		return razonBloqueo;
	}

	public void setRazonBloqueo(String razonBloqueo) {
		this.razonBloqueo = razonBloqueo;
	}

	public Date getFechaBloqueo() {
		return fechaBloqueo;
	}

	public void setFechaBloqueo(Date fechaBloqueo) {
		this.fechaBloqueo = fechaBloqueo;
	}

	public String getTokenDispositivo() {
		return tokenDispositivo;
	}

	public void setTokenDispositivo(String tokenDispositivo) {
		this.tokenDispositivo = tokenDispositivo;
	}

	public String getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(String preferencias) {
		this.preferencias = preferencias;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public NivelEducativo getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(NivelEducativo nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public FuenteRegistro getFuenteRegistro() {
		return fuenteRegistro;
	}

	public void setFuenteRegistro(FuenteRegistro fuenteRegistro) {
		this.fuenteRegistro = fuenteRegistro;
	}

	public Date getUltimaActualizacionPreferencias() {
		return ultimaActualizacionPreferencias;
	}
	
	public void setUltimaActualizacionPreferencias(Date ultimaActualizacionPreferencias) {
		this.ultimaActualizacionPreferencias = ultimaActualizacionPreferencias;
	}
    
	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getEstadoExtranjero() {
		return estadoExtranjero;
	}

	public void setEstadoExtranjero(String estadoExtranjero) {
		this.estadoExtranjero = estadoExtranjero;
	}

	public String getCiudadExtranjera() {
		return ciudadExtranjera;
	}

	public void setCiudadExtranjera(String ciudadExtranjera) {
		this.ciudadExtranjera = ciudadExtranjera;
	}

	public String getOtroPais() {
		return otroPais;
	}

	public void setOtroPais(String otroPais) {
		this.otroPais = otroPais;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public boolean isTerminosCondiciones() {
		return terminosCondiciones;
	}

	public void setTerminosCondiciones(boolean terminosCondiciones) {
		this.terminosCondiciones = terminosCondiciones;
	}	
				
}
