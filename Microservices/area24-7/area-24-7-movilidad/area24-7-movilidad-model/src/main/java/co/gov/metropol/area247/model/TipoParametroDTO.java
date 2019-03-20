package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class TipoParametroDTO extends AbstractDTO {

	private Long orden;

	private String nombre;

	private String valor;

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public TipoParametroDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoParametroDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

}
