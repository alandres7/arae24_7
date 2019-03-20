package co.gov.metropol.area247.contenedora.model.dto;

import java.util.Date;
import java.util.List;

public class CapaMenuDto {

	private Long id;
	private String nombre;
	private boolean activo;
	private boolean favorito;
	private Date ultimaActualizacion;
	private String rutaIconoCapa;
	private String rutaIconoMarker;
	private boolean tieneCategorias;
	private boolean tieneMarcadores;
	private String nombreTipoCapa;
	//Para menú
	public CapaMenuDto(Long id, String nombre, boolean activo, boolean favorito, Date ultimaActualizacion, String icono)
	{
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.favorito = favorito;
		this.ultimaActualizacion = ultimaActualizacion;
		this.rutaIconoCapa = icono;
	}
	
	//Para menú V2
	public CapaMenuDto(Long id, String nombre, boolean activo, boolean favorito, Date ultimaActualizacion, Long icono,
			Long iconoMarcador, String nombreTipoCapa)
	{
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.favorito = favorito;
		this.ultimaActualizacion = ultimaActualizacion;
		this.rutaIconoCapa = ""+icono;
		this.rutaIconoMarker = ""+iconoMarcador;
		this.nombreTipoCapa = nombreTipoCapa;
	}
	
	//Para preferencias de usuario
	public CapaMenuDto(Long id, String nombre, boolean activo, boolean favorito, Date ultimaActualizacion)
	{
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.favorito = favorito;
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public CapaMenuDto() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public Long getId() {
		return id;
	}

	public String getRutaIconoCapa() {
		return rutaIconoCapa;
	}

	public void setRutaIconoCapa(String rutaIconoCapa) {
		this.rutaIconoCapa = rutaIconoCapa;
	}

	public String getNombreTipoCapa() {
		return nombreTipoCapa;
	}

	public void setNombreTipoCapa(String nombreTipoCapa) {
		this.nombreTipoCapa = nombreTipoCapa;
	}	

	public String getRutaIconoMarker() {
		return rutaIconoMarker;
	}

	public void setRutaIconoMarker(String rutaIconoMarker) {
		this.rutaIconoMarker = rutaIconoMarker;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isTieneCategorias() {
		return tieneCategorias;
	}

	public void setTieneCategorias(boolean tieneCategorias) {
		this.tieneCategorias = tieneCategorias;
	}

	public boolean isTieneMarcadores() {
		return tieneMarcadores;
	}

	public void setTieneMarcadores(boolean tieneMarcadores) {
		this.tieneMarcadores = tieneMarcadores;
	}

	/*public List<CategoriaDtoSinListas> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaDtoSinListas> categorias) {
		this.categorias = categorias;
	}*/
	
	

	
}
