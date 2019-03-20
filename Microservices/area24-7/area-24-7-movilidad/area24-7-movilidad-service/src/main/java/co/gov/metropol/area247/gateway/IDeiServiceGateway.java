package co.gov.metropol.area247.gateway;

import co.gov.metropol.area247.services.rest.dei.DeiWSDTO;

public interface IDeiServiceGateway {
	
	/**
	 * Obtener informacion de la circulacion de vehiculos del servicio dei
	 * @return DeiWSDTO
	 */
	DeiWSDTO consultarDei();
	
}
