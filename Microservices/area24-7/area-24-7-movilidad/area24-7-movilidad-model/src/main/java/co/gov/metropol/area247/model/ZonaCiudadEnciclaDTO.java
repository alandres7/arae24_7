package co.gov.metropol.area247.model;

import java.util.ArrayList;
import java.util.List;

import co.gov.metropol.area247.annotations.Loggable;
import co.gov.metropol.area247.model.abstracts.AbstractDTO;

public class ZonaCiudadEnciclaDTO extends AbstractDTO{
	
	@Loggable
	private Long idZonaCiudad;
	
	@Loggable
	private String nombre;
	
	@Loggable
	private String descripcion;
	
	@Loggable
	private List<EstacionEnciclaDTO> estacionesEncicla = new ArrayList<>();
	
	@Override
	public ZonaCiudadEnciclaDTO withId(Long id) {
		super.setId(id);
		return null;
	}

	@Override
	public ZonaCiudadEnciclaDTO withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdZonaCiudad() {
		return idZonaCiudad;
	}

	public ZonaCiudadEnciclaDTO setIdZonaCiudad(Long idZonaCiudad) {
		this.idZonaCiudad = idZonaCiudad;
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public ZonaCiudadEnciclaDTO setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	
	public List<EstacionEnciclaDTO> getEstacionesEncicla() {
		return estacionesEncicla;
	}

	public void setEstacionesEncicla(List<EstacionEnciclaDTO> estacionesEncicla) {
		this.estacionesEncicla = estacionesEncicla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
