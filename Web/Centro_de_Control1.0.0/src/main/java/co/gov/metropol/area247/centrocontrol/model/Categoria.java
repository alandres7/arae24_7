package co.gov.metropol.area247.centrocontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/** Clase encargada de almacenar las diversos categorias que usara el centro de control */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Categoria {
	
	private Long  id;     
	private String nombre;
	private String descripcion;
	private Icono icono;
	private Icono iconoMarcador;
	private TipoCapa tipoCategoria;
	private String urlRecurso;
	private String aliasNombre;
	private String aliasMunicipio;
	private String aliasDescripcion;
	private String aliasImagen;
	private String aliasDireccion;
	private boolean fichaCaracterizacion;
	private String respuestaFichaCarac;
	private boolean poligono;
	private boolean msgOrdenamiento;
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

	public TipoCapa getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(TipoCapa tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}

	public boolean isFichaCaracterizacion() {
		return fichaCaracterizacion;
	}

	public void setFichaCaracterizacion(boolean fichaCaracterizacion) {
		this.fichaCaracterizacion = fichaCaracterizacion;
	}

	public String getRespuestaFichaCarac() {
		return respuestaFichaCarac;
	}

	public void setRespuestaFichaCarac(String respuestaFichaCarac) {
		this.respuestaFichaCarac = respuestaFichaCarac;
	}

	public boolean isPoligono() {
		return poligono;
	}

	public void setPoligono(boolean poligono) {
		this.poligono = poligono;
	}

	public boolean isMsgOrdenamiento() {
		return msgOrdenamiento;
	}

	public void setMsgOrdenamiento(boolean msgOrdenamiento) {
		this.msgOrdenamiento = msgOrdenamiento;
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