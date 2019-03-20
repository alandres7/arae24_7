package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class ModoEstacionDTO extends AbstractDTO {
	
	private String nombre;
	private String descripcion;

	@Override
	public ModoEstacionDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ModoEstacionDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public ModoEstacionDTO setNombre(String nombre) {
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
