package co.gov.metropol.area247.entorno.service;

public interface IEntornoEstacionService {
	boolean updateEstaciones(long idCapa);

	String getDetailXLocation(Long idCapa, double lat, double lng);
}
