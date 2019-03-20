package co.gov.metropol.area247.model;

import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class OperadorEmpresaDTO extends AbstractDTO{

	private Long idItem;
	private Long idOperador;
	private Long idEmpresaTransporte;
	private String activo;
	
	public OperadorEmpresaDTO() {
	}
	
	public OperadorEmpresaDTO(Long idOperador, Long idEmpresaTransporte, String activo) {
		this.idOperador = idOperador;
		this.idEmpresaTransporte = idEmpresaTransporte;
		this.activo = activo;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Long getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(Long idOperador) {
		this.idOperador = idOperador;
	}

	public Long getIdEmpresaTransporte() {
		return idEmpresaTransporte;
	}

	public void setIdEmpresaTransporte(Long idEmpresaTransporte) {
		this.idEmpresaTransporte = idEmpresaTransporte;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	@Override
	public OperadorEmpresaDTO  withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public OperadorEmpresaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		OperadorEmpresaDTO dto = (OperadorEmpresaDTO) obj;

		return (this.getIdOperador() == dto.getIdOperador()
				&& this.getIdEmpresaTransporte() == dto.getIdEmpresaTransporte());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
