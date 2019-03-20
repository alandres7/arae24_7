package co.gov.metropol.area247.service;

import java.util.List;

public interface ITarifaIntegradaMetroService {

	/**
	 * Obtiene la tarifa según la combinacion de los modos de transporte
	 * 
	 * @param idsModosTransporte
	 *            - ids de los modos de transporte por el cual se realizara la
	 *            busqueda de la tafifa
	 * 
	 * @return la tarifa que aplica para la combinacion
	 */
	Double obtenerTarifaPorCombinaciones(List<Long> idsModosTransporte);

}
