package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_EMPRESA_RUTA", schema = "MOVILIDAD")
public class EmpresaRuta extends Entities {
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_EMPRESA_TRANSPORTE")
	private Long idEmpresaTransporte;
	
	@Column(name = "ID_RUTA")
	private Long idRuta;
	
	@Column(name = "S_ACTIVO")
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
	public EmpresaRuta withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EmpresaRuta withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}
	
	
}
