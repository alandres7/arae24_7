package co.gov.metropol.area247.gateway;

import java.util.Date;

import co.gov.metropol.area247.services.rest.opt.PosibilidadViajeWSDTO;

public interface IPosibilidadViajeServiceGateway {

	/**
	 * Este metodo se encarga de obtener los posibles viajes que se pueden
	 * realizar a partir de un punto de origen hasta un punto de destino
	 * indicando un modo para transportarce(autobus, linea, ferreo, caminando,
	 * otros)
	 * 
	 * @param fecha
	 *            - fecha en la que se realiza la solicitud
	 * @param longitudOrigen
	 *            - longitud del punto de origen
	 * @param latitudOrigen
	 *            - latitud del punto de origen
	 * @param longitudDestino
	 *            - longitud del punto de destino
	 * @param latitudDestino
	 *            - latitud del punto de destino
	 * @param modosTransporte
	 *            - cadena que contiene los modos de transporte (e.g WALK,
	 *            TRANSIT, BUS, RAIL, etc)
	 * 
	 * @return un objeto tipo {@link PosibilidadViajeWSDTO}
	 */
	PosibilidadViajeWSDTO consultarPosiblesViajes(Date fecha, Double longitudOrigen, Double latitudOrigen,
			Double longitudDestino, Double latitudDestino, String modosTransporte);

}
