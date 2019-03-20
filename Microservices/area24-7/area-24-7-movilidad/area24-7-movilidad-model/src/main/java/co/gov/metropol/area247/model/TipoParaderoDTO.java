package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class TipoParaderoDTO extends AbstractDTO {

	private Long idItem;
	private int fuenteDatos;
	private String nombre;
	private String descripcion;

	@Override
	public TipoParaderoDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoParaderoDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
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

	public String getNombre() {
		return nombre;
	}

	public TipoParaderoDTO setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
