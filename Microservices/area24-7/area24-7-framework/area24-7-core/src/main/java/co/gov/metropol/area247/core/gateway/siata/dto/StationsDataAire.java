package co.gov.metropol.area247.core.gateway.siata.dto;

import java.util.List;

import com.google.gson.Gson;

public class StationsDataAire implements IStationsData<StationAire>{
	private String mensaje;
	private List<StationAire> datos;
	
	@Override
	public String getMensaje() {
		return mensaje;
	}
	@Override
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	@Override
	public List<StationAire> getDatos() {
		return datos;
	}
	@Override
	public void setDatos(List<StationAire> datos) {
		this.datos = datos;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
