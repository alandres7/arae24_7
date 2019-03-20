package co.gov.metropol.area247.model;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class DisponibilidadCiclaDTO extends AbstractDTO {

	/*
	 * Identificador unico de la estacion
	 */
	@Loggable
	private Long idEstacionEncicla;

	/**
	 * Puestos disponibles de la estación
	 */
	private Long puestosEstacionEncila;

	/**
	 * Cantidad de bicicletas
	 */
	private Long cantidadBicicletas;

	@Override
	public DisponibilidadCiclaDTO withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public DisponibilidadCiclaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdEstacionEncicla() {
		return idEstacionEncicla;
	}

	public void setIdEstacionEncicla(Long idEstacionEncicla) {
		this.idEstacionEncicla = idEstacionEncicla;
	}

	public Long getPuestosEstacionEncila() {
		return puestosEstacionEncila;
	}

	public void setPuestosEstacionEncila(Long puestosEstacionEncila) {
		this.puestosEstacionEncila = puestosEstacionEncila;
	}

	public Long getCantidadBicicletas() {
		return cantidadBicicletas;
	}

	public void setCantidadBicicletas(Long cantidadBicicletas) {
		this.cantidadBicicletas = cantidadBicicletas;
	}

}
