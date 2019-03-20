package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.VentanaInformacion;

public interface IContenedoraVentanaInformacionService {
	
	VentanaInformacion ventanaInformacionCrear(VentanaInformacion ventanaInformacion);
	boolean ventanaInformacionActualizar(VentanaInformacion ventanaInformacion);
	boolean ventanaInformacionEliminar(Long idVentanaInformacion);
	List<VentanaInformacion> ventanaInformacionObtenerTodas();
	VentanaInformacion ventanaInformacionObtenerPorNombre(String nombreVentanaInformacion);
	VentanaInformacion ventanaInformacionObtenerPorId(Long idVentanaInformacion);

}
