package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class TipoLineaDTO extends AbstractDTO {

	private Long idTipoLinea;
	private String nombre;
	private String descripcion;

	@Override
	public TipoLineaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoLineaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
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

	public Long getIdTipoLinea() {
		return idTipoLinea;
	}

	public void setIdTipoLinea(Long idTipoLinea) {
		this.idTipoLinea = idTipoLinea;
	}

}
