package co.gov.metropol.area247.centrocontrol.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/** Clase encargada de almacenar las diversos capas que usara el centro de control */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Capa implements Serializable{	

	private static final long serialVersionUID = 1L;
	private Long  id;     
    private String nombre; 
    private String descripcion;    
    private Boolean activo;
    private Boolean favorito;
    private Icono icono; 
    private Icono iconoMarcador;
	private TipoCapa tipoCapa;
	private Long  idAplicacion;
	private String urlRecurso;
	private String aliasNombre;
	private String aliasMunicipio;
	private String aliasDescripcion;
	private String aliasCategoria;
	private String aliasImagen;
	private String aliasDireccion;
	private boolean fichaCaracterizacion;
	private boolean poligono;
	private boolean tieneHistoria;
	
	private String rutaIconoAprobado;
	private String rutaIconoRechazado;
	private String rutaIconoPendiente;
    
		
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
    public Boolean getFavorito() {
		return favorito;
	}

	public void setFavorito(Boolean favorito) {
		this.favorito = favorito;
	}		

	public TipoCapa getTipoCapa() {
		return tipoCapa;
	}

	public void setTipoCapa(TipoCapa tipoCapa) {
		this.tipoCapa = tipoCapa;
	}
	
	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
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
		
	public boolean isTieneHistoria() {
		return tieneHistoria;
	}

	public void setTieneHistoria(boolean tieneHistoria) {
		this.tieneHistoria = tieneHistoria;
	}

	public String getRutaIconoAprobado() {
		return rutaIconoAprobado;
	}

	public void setRutaIconoAprobado(String rutaIconoAprobado) {
		this.rutaIconoAprobado = rutaIconoAprobado;
	}

	public String getRutaIconoRechazado() {
		return rutaIconoRechazado;
	}

	public void setRutaIconoRechazado(String rutaIconoRechazado) {
		this.rutaIconoRechazado = rutaIconoRechazado;
	}

	public String getRutaIconoPendiente() {
		return rutaIconoPendiente;
	}

	public void setRutaIconoPendiente(String rutaIconoPendiente) {
		this.rutaIconoPendiente = rutaIconoPendiente;
	}	  
		
}