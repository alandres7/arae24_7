package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class EmpresaRutaDTO extends AbstractDTO {

	private Long idEmpresaTransporte;
	private Long idRuta;
	private String activo;

	public Long getIdEmpresaTransporte() {
		return idEmpresaTransporte;
	}

	public void setIdEmpresaTransporte(Long idEmpresaTransporte) {
		this.idEmpresaTransporte = idEmpresaTransporte;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	@Override
	public EmpresaRutaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EmpresaRutaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

}
