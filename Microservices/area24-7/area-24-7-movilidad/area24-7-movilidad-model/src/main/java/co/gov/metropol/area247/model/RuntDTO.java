package co.gov.metropol.area247.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class RuntDTO extends AbstractDTO{
	
	@NotNull
	@Size(max = 11)
	@Loggable
	private String placa;
	
	@NotNull
	@Size(max = 4)
	@Loggable
	private Long modelo;
	
	@NotNull
	@Size(max = 10)
	@Loggable
	private String tipoConbustible;
	
	@NotNull
	@Size(max = 4)
	@Loggable
	private Long ejes;
	
	@NotNull
	@Size(max = 10)
	@Loggable
	private Long capacidadCarga;
	
	@NotNull
	@Size(max = 50)
	@Loggable
	private String organizmoTransito;
	
	@Size(max = 20)
	@Loggable
	private String clase;

	@Override
	public RuntDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public RuntDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getPlaca() {
		return placa;
	}

	public RuntDTO setPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	public Long getModelo() {
		return modelo;
	}

	public RuntDTO setModelo(Long modelo) {
		this.modelo = modelo;
		return this;
	}

	public String getTipoConbustible() {
		return tipoConbustible;
	}

	public RuntDTO setTipoConbustible(String tipoConbustible) {
		this.tipoConbustible = tipoConbustible;
		return this;
	}

	public Long getEjes() {
		return ejes;
	}

	public RuntDTO setEjes(Long ejes) {
		this.ejes = ejes;
		return this;
	}

	public Long getCapacidadCarga() {
		return capacidadCarga;
	}

	public RuntDTO setCapacidadCarga(Long capacidadCarga) {
		this.capacidadCarga = capacidadCarga;
		return this;
	}

	public String getOrganizmoTransito() {
		return organizmoTransito;
	}

	public RuntDTO setOrganizmoTransito(String organizmoTransito) {
		this.organizmoTransito = organizmoTransito;
		return this;
	}

	public String getClase() {
		return clase;
	}

	public RuntDTO setClase(String clase) {
		this.clase = clase;
		return this;
	}

}
