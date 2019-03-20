package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.PuntoTarjetaCivicaDTO;



public interface IPuntoTarjetaCivicaService {
	
	/**
	 * Obtener los puntos de tarjeta civica dada las coordenadas de un usuario
	 * 
	 * @param latitud  - latitud donde se encuentra parado el usuario.
	 * @param longitud  - longitud donde se encuentra parado el usuario
	 * @param radio    - radio de busqueda designado por el usuario
	 * @return List type PuntoTarjetaCivicaDTO
	 */
	List<PuntoTarjetaCivicaDTO> obtenerEstacionesCercanas(Double latitud, Double longitud, Double radio);
}
