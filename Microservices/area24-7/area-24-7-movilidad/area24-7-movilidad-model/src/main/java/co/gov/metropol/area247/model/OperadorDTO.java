package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class OperadorDTO extends AbstractDTO{
	private Long idItem;
	private Long idSistemaRuta;
	private String activo;
	
	public Long getIdItem() {
		return idItem;
	}
	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}
	public Long getIdSistemaRuta() {
		return idSistemaRuta;
	}
	public void setIdSistemaRuta(Long idSistemaRuta) {
		this.idSistemaRuta = idSistemaRuta;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	@Override
	public OperadorDTO withId(Long id) {
		super.setId(id);
		return this;
	}
	@Override
	public OperadorDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return null;
	}
}
