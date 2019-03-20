package co.gov.metropol.area247.contenedora.model.dto;

import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.TipoCapa;

public class CategoriaDto {
	private Long id;
	private String nombre;
	private String descripcion;
	private Icono icono;
	private Icono iconoMarcador;
	private String urlRecurso;
	private String aliasNombre;
	private String aliasMunicipio;
	private String aliasDescripcion;
	private String aliasTipo;
	private String aliasImagen;
	private String aliasDireccion;
    private boolean fichaCaracterizacion;
	private String respuestaFichaCarac;
	private TipoCapa tipoCategoria;
	private boolean poligono;
	private boolean msgOrdenamiento;
	private Boolean tieneHistoria;
	
	public CategoriaDto(Long id, String nombre, String descripcion, Icono icono, Icono iconoMarcador,
			String urlRecurso, String aliasNombre, String aliasMunicipio, String aliasDescripcion, 
			String aliasTipo, String aliasImagen, String aliasDireccion, boolean fichaCaracterizacion, 
			String respuestaFichaCarac, TipoCapa tipoCategoria, Boolean tieneHistoria, 
			boolean poligono, boolean msgOrdenamiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.icono = icono;
		this.iconoMarcador = iconoMarcador;
		this.urlRecurso = urlRecurso;
		this.aliasNombre = aliasNombre;
		this.aliasMunicipio = aliasMunicipio;
		this.aliasDescripcion = aliasDescripcion;
		this.aliasTipo = aliasTipo;
		this.aliasImagen = aliasImagen;
		this.aliasDireccion = aliasDireccion;
		this.fichaCaracterizacion = fichaCaracterizacion;
		this.respuestaFichaCarac = respuestaFichaCarac;
		this.tipoCategoria = tipoCategoria;
		this.tieneHistoria = tieneHistoria;
		this.poligono = poligono;
		this.msgOrdenamiento = msgOrdenamiento;
	}
	
	
	public CategoriaDto(Categoria categoria) {
		this.id = categoria.getId();
		this.nombre = categoria.getNombre();
		this.descripcion = categoria.getDescripcion();
		this.icono = categoria.getIcono();
		this.iconoMarcador = categoria.getIconoMarcador();
		this.urlRecurso = categoria.getUrlRecurso();
		this.aliasNombre = categoria.getAliasNombre();
		this.aliasMunicipio = categoria.getAliasMunicipio();
		this.aliasDescripcion = categoria.getAliasDescripcion();
		this.aliasTipo = categoria.getAliasTipo();
		this.aliasImagen = categoria.getAliasImagen();
		this.aliasDireccion = categoria.getAliasDireccion();
		this.fichaCaracterizacion = categoria.isFichaCaracterizacion();
		this.respuestaFichaCarac = categoria.getRespuestaFichaCarac();
		this.tipoCategoria = categoria.getTipoCategoria();
		this.poligono = categoria.isPoligono();
		this.msgOrdenamiento = categoria.isMsgOrdenamiento();
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
	
	public String getAliasTipo() {
		return aliasTipo;
	}
	
	public void setAliasTipo(String aliasTipo) {
		this.aliasTipo = aliasTipo;
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
	
	public String getRespuestaFichaCarac() {
		return respuestaFichaCarac;
	}
	
	public void setRespuestaFichaCarac(String respuestaFichaCarac) {
		this.respuestaFichaCarac = respuestaFichaCarac;
	}
	
	public TipoCapa getTipoCategoria() {
		return tipoCategoria;
	}
	
	public void setTipoCategoria(TipoCapa tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
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
	public Boolean getTieneHistoria() {
		return tieneHistoria;
	}
	public void setTieneHistoria(Boolean tieneHistoria) {
		this.tieneHistoria = tieneHistoria;
	}

}
