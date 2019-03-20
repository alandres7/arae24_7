package co.gov.metropol.area247.core.ordenamiento.dto;

public class CategoryRelationship {
	
	private String nombreCategoria;
	
	private String nombre;
	
	private String descripcion;
	
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
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
	/**
	 * 
	 */
	public CategoryRelationship() {
		// TODO Auto-generated constructor stub
	}
	public CategoryRelationship(String nombreCategoria, String nombre, String descripcion) {
		this.nombreCategoria = nombreCategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	
	
}
