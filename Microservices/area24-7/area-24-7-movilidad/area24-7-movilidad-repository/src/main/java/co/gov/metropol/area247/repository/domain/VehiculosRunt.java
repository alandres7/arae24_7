package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "TSIMAUD_VEHICULOS_RUNT", schema = "MOVILIDAD")
public class VehiculosRunt  extends Entities {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "S_PLACA", nullable = false, length = 11)
	private String placa;
	
	@Column(name = "N_MODELO", nullable = false, length = 4)
	private Long modelo;
	
	@Column(name = "S_TIPO_CONBUSTIBLE", nullable = true, length = 10)
	private String tipoConbustible;
	
	@Column(name = "N_EJES", nullable = true, length = 4)
	private Long ejes;
	
	@Column(name = "N_CAPACIDAD_CARGA", nullable = true, length = 10)
	private Long capacidadCarga;
	
	@Column(name = "S_ORGANIZMO_TRANSITO", nullable = true, length = 50)
	private String organizmoTransito;
	
	@Column(name = "S_CLASE", nullable = true, length = 20)
	private String clase;

	@Override
	public VehiculosRunt withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public VehiculosRunt withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getPlaca() {
		return placa;
	}

	public VehiculosRunt setPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	public Long getModelo() {
		return modelo;
	}

	public VehiculosRunt setModelo(Long modelo) {
		this.modelo = modelo;
		return this;
	}

	public String getTipoConbustible() {
		return tipoConbustible;
	}

	public VehiculosRunt setTipoConbustible(String tipoConbustible) {
		this.tipoConbustible = tipoConbustible;
		return this;
	}

	public Long getEjes() {
		return ejes;
	}

	public VehiculosRunt setEjes(Long ejes) {
		this.ejes = ejes;
		return this;
	}

	public Long getCapacidadCarga() {
		return capacidadCarga;
	}

	public VehiculosRunt setCapacidadCarga(Long capacidadCarga) {
		this.capacidadCarga = capacidadCarga;
		return this;
	}

	public String getOrganizmoTransito() {
		return organizmoTransito;
	}

	public VehiculosRunt setOrganizmoTransito(String organizmoTransito) {
		this.organizmoTransito = organizmoTransito;
		return this;
	}

	public String getClase() {
		return clase;
	}

	public VehiculosRunt setClase(String clase) {
		this.clase = clase;
		return this;
	}

}
