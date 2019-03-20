package co.gov.metropol.area247.contenedora.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table (name = "D247CON_APLICACION", schema = "CONTENEDOR")
public class Aplicacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_APLICACION_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	private Long id;
	
	@JsonProperty
	@ApiModelProperty(notes = "Nombre identificador único de la aplicación", required = true)
	@NotNull
	@Size(max = 100)
	@Column(name = "S_NOMBRE", unique = false)
	private String nombre;
	
	@JsonProperty
	@ApiModelProperty(notes = "Describe en qué consiste la aplicación", required = false)
	@Column(name = "S_DESCRIPCION")
	@Size(max = 2000)
	private String descripcion;
	
	@JsonProperty
	@ApiModelProperty(notes = "Código identificador único de la aplicación, incluye el '#' inicial", required = true)
	@NotNull
	@Size(max = 10)
	@Column(name = "S_CODIGO_COLOR", unique = true)
	private String codigoColor;
	
	@JsonProperty
	@ApiModelProperty(notes = "Código identificador del switche que activa/desactiva un elemento", required = true)
	@NotNull
	@Size(max = 100)
	@Column(name = "S_CODIGO_TOGGLE", unique = true)
	private String codigoToggle;
	
	@JsonProperty
	@ApiModelProperty(notes = "Establece si la aplicación se encuentra activa para visualización", required = true)
	@NotNull
	@Column(name = "N_ACTIVO")
	private boolean activo;
	
	@JsonProperty
	@ApiModelProperty(notes = "Establece si la aplicación es la que se mostrará por defecto luego del ingreso", required = true)
	@Column(name = "N_RADIO_ACCION")
	private Integer defaultRadius;
	
	@JsonProperty
	@Column(name = "N_RADIO_MINIMO")
	private Integer minRadius;
	
	@JsonProperty
	@Column(name = "N_RADIO_MAXIMO")
	private Integer maxRadius;
	
	@JsonProperty
	@Column(name = "S_OTRAS_PREFERENCIAS")
	private String otherPrefs;
	
	@ApiModelProperty(notes = "Describe la fecha de la última actualización realizada", required = false)
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	@Column(name = "D_ULTIMA_ACTUALIZACION")
	private Date ultimaActualizacion;
		
	@OneToMany(mappedBy="aplicacion", cascade=CascadeType.ALL)
	private List<Capa> capas;
	
	@JsonProperty
	@ApiModelProperty(notes = "Información del ícono representativo de la aplicación", required = true)
	@NotNull
	@OneToOne
	@JoinColumn(name = "ID_ICONO", referencedColumnName = "ID")
	private Icono icono;
	
	@JsonProperty
	@ApiModelProperty(notes = "Describe mensajes a visualizarse una vez se inicia la aplicación", required = false)
	@OneToMany(mappedBy="aplicacion", cascade = CascadeType.ALL)
	private List<Recomendacion> recomendaciones;
	
	public String getOtherPrefs() {
		return otherPrefs;
	}

	public void setOtherPrefs(String otherPrefs) {
		this.otherPrefs = otherPrefs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCodigoColor() {
		return codigoColor;
	}

	public void setCodigoColor(String codigoColor) {
		this.codigoColor = codigoColor;
	}

	public List<Capa> getCapas() {
		return capas;
	}

	public void setCapas(List<Capa> capas) {
		this.capas = capas;
	}

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public List<Recomendacion> getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(List<Recomendacion> recomendaciones) {
		this.recomendaciones = recomendaciones;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public String getCodigoToggle() {
		return codigoToggle;
	}

	public void setCodigoToggle(String codigoToggle) {
		this.codigoToggle = codigoToggle;
	}	
	
	public Integer getDefaultRadius() {
		return defaultRadius;
	}

	public Integer getMinRadius() {
		return minRadius;
	}

	public Integer getMaxRadius() {
		return maxRadius;
	}

	public void setDefaultRadius(Integer defaultRadius) {
		this.defaultRadius = defaultRadius;
	}

	public void setMinRadius(Integer minRadius) {
		this.minRadius = minRadius;
	}

	public void setMaxRadius(Integer maxRadius) {
		this.maxRadius = maxRadius;
	}
}
