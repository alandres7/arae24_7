package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class EmpresaTransporteDTO extends AbstractDTO{

	private Long idItem;
	private String nombre;
	private String activo;

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	@Override
	public EmpresaTransporteDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EmpresaTransporteDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

}
