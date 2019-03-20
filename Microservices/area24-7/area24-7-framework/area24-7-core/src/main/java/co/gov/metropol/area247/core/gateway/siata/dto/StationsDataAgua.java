package co.gov.metropol.area247.core.gateway.siata.dto;

import java.util.List;

public class StationsDataAgua implements IStationsData<StationAgua> {
	private String mensaje;
	private List<StationAgua> datos = null;
	@Override
	public String getMensaje() {
	return mensaje;
	}
	@Override
	public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
	}
	@Override
	public List<StationAgua> getDatos() {
	return datos;
	}
	@Override
	public void setDatos(List<StationAgua> datos) {
	this.datos = datos;
	}
}
