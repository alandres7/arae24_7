package co.gov.metropol.area247.core.gateway.siata.dto;

import java.util.List;


public interface IStationsData<T> {
	public List<T> getDatos();
	public String getMensaje();
	public void setDatos(List<T> datos);
	public void setMensaje(String mensaje);
}
