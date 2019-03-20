package co.gov.metropol.area247.contenedora.model.dto;

import java.util.Date;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.TipoCapa;

public class CapaDto {
	private Long id;
	private String nombre;
	private String descripcion;
	private boolean activo;
	private boolean favorito;
	private Date ultimaActualizacion;
	private Icono icono;
	private Icono iconoMarcador;
	private String urlRecurso;
	private String aliasNombre;
	private String aliasMunicipio;
	private String aliasDescripcion;
	private String aliasCategoria;
	private String aliasImagen;
	private String aliasDireccion;
	private TipoCapa tipoCapa;
	private boolean fichaCaracterizacion;
	private boolean poligono;
	private Boolean tieneHistoria;
	
	public CapaDto(Long id, String nombre, String descripcion, boolean activo, boolean favorito, 
			Boolean tieneHistoria, Date ultimaActualizacion, Icono icono, Icono iconoMarcador, 
			String urlRecurso, String aliasNombre, String aliasMunicipio, String aliasDescripcion, 
			String aliasCategoria, String aliasImagen, String aliasDireccion, TipoCapa tipoCapa, 
			boolean fichaCaracterizacion, boolean poligono) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.activo = activo;
		this.favorito = favorito;
		this.tieneHistoria = tieneHistoria;
		this.ultimaActualizacion = ultimaActualizacion;
		this.icono = icono;
		this.iconoMarcador = iconoMarcador;
		this.urlRecurso = urlRecurso;
		this.aliasNombre = aliasNombre;
		this.aliasMunicipio = aliasMunicipio;
		this.aliasDescripcion = aliasDescripcion;
		this.aliasCategoria = aliasCategoria;
		this.aliasImagen = aliasImagen;
		this.aliasDireccion = aliasDireccion;
		this.tipoCapa = tipoCapa;
		this.fichaCaracterizacion = fichaCaracterizacion;
		this.poligono = poligono;
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
	
	public Icono getIcono() {
		return icono;
	}
	
	public void setIcono(Icono icono) {
		this.icono = icono;
	}
			
	public Icono getIconoMarcador() {
		return iconoMarcador;
	}

	public void setIconoMarcador(Icono iconoMarcador) {
		this.iconoMarcador = iconoMarcador;
	}
	
	public String getUrlRecurso() {
		return urlRecurso;
	}

	public void setUrlRecurso(String urlRecurso) {
		this.urlRecurso = urlRecurso;
	}

	public String getAliasNombre() {
		return aliasNombre;
	}

	public void setAliasNombre(String aliasNombre) {
		this.aliasNombre = aliasNombre;
	}

	public String getAliasMunicipio() {
		return aliasMunicipio;
	}

	public void setAliasMunicipio(String aliasMunicipio) {
		this.aliasMunicipio = aliasMunicipio;
	}

	public String getAliasDescripcion() {
		return aliasDescripcion;
	}

	public void setAliasDescripcion(String aliasDescripcion) {
		this.aliasDescripcion = aliasDescripcion;
	}	

	public String getAliasCategoria() {
		return aliasCategoria;
	}

	public void setAliasCategoria(String aliasCategoria) {
		this.aliasCategoria = aliasCategoria;
	}
	
		public String getAliasImagen() {
		return aliasImagen;
	}

	public void setAliasImagen(String aliasImagen) {
		this.aliasImagen = aliasImagen;
	}
	
	public String getAliasDireccion() {
		return aliasDireccion;
	}

	public void setAliasDireccion(String aliasDireccion) {
		this.aliasDireccion = aliasDireccion;
	}

	public TipoCapa getTipoCapa() {
		return tipoCapa;
	}
	
	public void setTipoCapa(TipoCapa tipoCapa) {
		this.tipoCapa = tipoCapa;
	}

	public boolean isFichaCaracterizacion() {
		return fichaCaracterizacion;
	}

	public void setFichaCaracterizacion(boolean fichaCaracterizacion) {
		this.fichaCaracterizacion = fichaCaracterizacion;
	}

	public boolean isPoligono() {
		return poligono;
	}

	public void setPoligono(boolean poligono) {
		this.poligono = poligono;
	}

	public Boolean getTieneHistoria() {
		return tieneHistoria;
	}

	public void setTieneHistoria(Boolean tieneHistoria) {
		this.tieneHistoria = tieneHistoria;
	}

}
