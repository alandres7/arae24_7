package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_OPERADOR_EMPRESA", schema = "MOVILIDAD")
public class OperadorEmpresa extends Entities{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "ID_OPERADOR")
	private Long idOperador;
	
	@Column(name = "ID_EMPRESA_TRANSPORTE")
	private Long idEmpresaTransporte;
	
	@Column(name = "S_ACTIVO")
	private String activo;
	
	

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
	public OperadorEmpresa withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public OperadorEmpresa withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return null;
	}

}
