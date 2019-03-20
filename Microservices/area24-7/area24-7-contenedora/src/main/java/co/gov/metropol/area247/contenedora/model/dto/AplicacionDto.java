package co.gov.metropol.area247.contenedora.model.dto;

import java.util.Date;
import java.util.List;

import co.gov.metropol.area247.contenedora.model.Icono;


public class AplicacionDto {

	private Long id;
	private String nombre;
	private String descripcion;
	private String codigoColor;
	private String codigoToggle;
	private Icono icono;
	private boolean activo;
	private Integer radioAccion;
	private Integer minRadius;
	private Integer maxRadius;
	private String otherPrefs;
	private Date ultimaActualizacion;
	private String rutaIcono;
	private List<CapaMenuDto> capas;
	private List<RecomendacionDto> recomendaciones;
	
	
	//para menú del centro de control
	public AplicacionDto(Long id
					   , String nombre
					   , String descripcion
					   , String codigoColor
					   , String codigoToggle
					   , boolean activo
					   , Integer radioAccion
					   , Integer minRadius
					   , Integer maxRadius
					   , String otherPrefs
					   , Icono icono) 
	{
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
		this.icono = icono;
	}

	//para menú
	public AplicacionDto(Long id
					   , String nombre
					   , String codigoColor
					   , String codigoToggle
					   , boolean activo
					   , Integer radioAccion
					   , Integer minRadius
					   , Integer maxRadius
					   , String otherPrefs
					   , Date ultimaActualizacion
					   , String rutaIcono) 
	{
		this.id = id;
		this.nombre = nombre;
		this.codigoColor = codigoColor;
		this.codigoToggle = codigoToggle;
		this.activo = activo;
		this.radioAccion = radioAccion;
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
		this.otherPrefs = otherPrefs;
		this.ultimaActualizacion = ultimaActualizacion;
		this.rutaIcono = rutaIcono;
	}
	
	//para prefrencias de usuario
	public AplicacionDto(Long id
			           , String nombre
			           , boolean activo
			           , Integer radioAccion
			           , Integer minRadius
			           , Integer maxRadius
			           , String otherPrefs
			           , Date ultimaActualizacion) 
	{
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.radioAccion = radioAccion;
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
		this.otherPrefs = otherPrefs;
		this.ultimaActualizacion = ultimaActualizacion;
	}
	
	//para asignar el color a los ventanas de información
	public AplicacionDto(Long id
			           , String nombre
			           , String codigoColor
			           , String codigoToggle
			           , boolean activo
			           , Integer radioAccion
			           , Integer minRadius
			           , Integer maxRadius
			           , String otherPrefs) 
	{
		this.id = id;
		this.nombre = nombre;
		this.codigoColor = codigoColor;
		this.codigoToggle = codigoToggle;
		this.activo = activo;
		this.radioAccion = radioAccion;
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
		this.otherPrefs = otherPrefs;
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
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Icono getIcono() {
		return icono;
	}

	public void setIcono(Icono icono) {
		this.icono = icono;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getRutaIcono() {
		return rutaIcono;
	}

	public void setRutaIcono(String rutaIcono) {
		this.rutaIcono = rutaIcono;
	}

	public List<RecomendacionDto> getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(List<RecomendacionDto> recomendaciones) {
		this.recomendaciones = recomendaciones;
	}
	
	public AplicacionDto() {
		// TODO Auto-generated constructor stub
	}

	public List<CapaMenuDto> getCapas() {
		return capas;
	}

	public void setCapas(List<CapaMenuDto> capas) {
		this.capas = capas;
	}
		
}
