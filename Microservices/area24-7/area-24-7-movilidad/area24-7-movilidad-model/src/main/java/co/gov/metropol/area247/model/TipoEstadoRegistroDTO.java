package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class TipoEstadoRegistroDTO extends AbstractDTO {

	private String nombre;
	private String descripcion;

	@Override
	public TipoEstadoRegistroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoEstadoRegistroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoEstadoRegistroDTO setNombre(String nombre) {
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
