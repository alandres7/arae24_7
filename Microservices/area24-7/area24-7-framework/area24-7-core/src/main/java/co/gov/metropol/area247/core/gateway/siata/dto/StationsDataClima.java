package co.gov.metropol.area247.core.gateway.siata.dto;

import java.util.List;

public class StationsDataClima implements IStationsData<StationClima> {
	private List<StationClima> datos;
	private String mensaje;
	@Override
	public List<StationClima> getDatos() {
		return this.datos;
	}

	@Override
	public String getMensaje() {
		return this.mensaje;
	}

	@Override
	public void setDatos(List<StationClima> datos) {
		this.datos = datos;
	}

	@Override
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
