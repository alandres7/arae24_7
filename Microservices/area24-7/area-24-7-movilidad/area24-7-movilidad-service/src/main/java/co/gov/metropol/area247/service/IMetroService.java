package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.services.rest.metro.LineaMetroWSDTO;

public interface IMetroService {

	/**
	 * Cargar la informacion del servicio de encicla en la base de datos
	 */
	void cargarDatosMetro();

	/**
	 * Procesa las lineas que obtiene del servicio del metro
	 * 
	 * @param lineasDTO
	 *            - {@link LineaMetroWSDTO}
	 * 
	 */
	void procesarLineas(List<LineaMetroDTO> lineasDTO);

}