package co.gov.metropol.area247.contenedora.model.dto;

import java.util.Date;

import co.gov.metropol.area247.contenedora.model.Icono;

public class AplicacionDtoSinListas {
	private Long id;
	private String nombre;
	private String descripcion;
	private String codigoColor;
	private String codigoToggle;
	private boolean activo;
	private Integer radioAccion;
	private Integer minRadius;
	private Integer maxRadius;
	private String otherPrefs;
	private Date ultimaActualizacion;
	private Icono icono;
	
	
	public AplicacionDtoSinListas(Long id
			                    , String nombre
			                    , String descripcion
			                    , String codigoColor
			                    , String codigoToggle
			                    , boolean activo
			                    , Integer radioAccion
			                    , Integer minRadius
			                    , Integer maxRadius
			                    , String otherPrefs
			                    , Date ultimaActualizacion
			                    , Icono icono) 
	{
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.codigoColor = codigoColor;
		this.codigoToggle = codigoToggle;
		this.activo = activo;
		this.radioAccion = radioAccion;
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
		this.otherPrefs = otherPrefs;
		this.ultimaActualizacion = ultimaActualizacion;
		this.icono = icono;
	}
	public String getOtherPrefs() {
		return otherPrefs;
	}
	public void setOtherPrefs(String otherPrefs) {
		this.otherPrefs = otherPrefs;
	}
	public Integer getMinRadius() {
		return minRadius;
	}
	public void setMinRadius(Integer minRadius) {
		this.minRadius = minRadius;
	}
	public Integer getMaxRadius() {
		return maxRadius;
	}
	public void setMaxRadius(Integer maxRadius) {
		this.maxRadius = maxRadius;
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
	public String getCodigoToggle() {
		return codigoToggle;
	}
	public void setCodigoToggle(String codigoToggle) {
		this.codigoToggle = codigoToggle;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Integer getRadioAccion() {
		return radioAccion;
	}
	public void setRadioAccion(Integer radioAccion) {
		this.radioAccion = radioAccion;
	}
	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}
	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}
	public Icono getIcono() {
		return icono;
	}
	public void setIcono(Icono icono) {
		this.icono = icono;
	}
	
}
