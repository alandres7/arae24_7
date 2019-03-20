package co.gov.metropol.area247.contenedora.model.dto;

import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Subcategoria;

public class SubcategoriaDtoSinListas {
	private Long id;
	private String nombre;
	private String descripcion;
	private Icono icono;
	private Icono iconoMarcador;
	private String urlRecurso;
	private String aliasNombre;
	private String aliasNombreItem;
	private String aliasDescripcionItem;
	private String aliasId;
	private boolean tieneMarcadores;
	
	public SubcategoriaDtoSinListas(Long id, String nombre, String descripcion, Icono icono, Icono iconoMarcador,
			String urlRecurso, String aliasNombre, String aliasNombreItem, String aliasDescripcionItem,
			String aliasId) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.icono = icono;
		this.iconoMarcador = iconoMarcador;
		this.urlRecurso = urlRecurso;
		this.aliasNombre = aliasNombre;
		this.aliasNombreItem = aliasNombreItem;
		this.aliasDescripcionItem = aliasDescripcionItem;
		this.aliasId = aliasId;
	}
	public SubcategoriaDtoSinListas(Subcategoria sub){
		
		this.id = sub.getId();
		this.nombre = sub.getNombre();
		this.descripcion = sub.getDescripcion();
		this.icono = sub.getIcono();
		this.iconoMarcador = sub.getIconoMarcador();
		this.urlRecurso = sub.getUrlRecurso();
		this.aliasNombre = sub.getAliasNombre();
		this.aliasNombreItem = sub.getAliasNombreItem();
		this.aliasDescripcionItem = sub.getAliasDescripcionItem();
		this.aliasId = sub.getAliasId();
		
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
	public String getAliasNombreItem() {
		return aliasNombreItem;
	}
	public void setAliasNombreItem(String aliasNombreItem) {
		this.aliasNombreItem = aliasNombreItem;
	}
	public String getAliasDescripcionItem() {
		return aliasDescripcionItem;
	}
	public void setAliasDescripcionItem(String aliasDescripcionItem) {
		this.aliasDescripcionItem = aliasDescripcionItem;
	}
	public String getAliasId() {
		return aliasId;
	}
	public void setAliasId(String aliasId) {
		this.aliasId = aliasId;
	}
	public boolean isTieneMarcadores() {
		return tieneMarcadores;
	}
	public void setTieneMarcadores(boolean tieneMarcadores) {
		this.tieneMarcadores = tieneMarcadores;
	}
}
