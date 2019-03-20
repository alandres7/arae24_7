package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class TipoRutaDTO extends AbstractDTO{

	private String nombre;
	private String descripcion;
	private Long idItem;
	private int fuenteDatos;
	
	
	
	public TipoRutaDTO() {
		this.nombre = "";
		this.descripcion = "";
		this.idItem = (long) 0;
		this.fuenteDatos = 0;
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

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	@Override
	public TipoRutaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoRutaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

}
