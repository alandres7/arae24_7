package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class ConCatUbicacionFavoritaDTO extends AbstractDTO {

	@Loggable
	private String codigo;

	@Loggable
	private String nombre;

	@Override
	public ConCatUbicacionFavoritaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ConCatUbicacionFavoritaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
