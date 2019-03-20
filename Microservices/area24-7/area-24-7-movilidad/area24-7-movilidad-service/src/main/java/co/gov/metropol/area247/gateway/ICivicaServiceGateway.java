package co.gov.metropol.area247.gateway;

import java.util.List;

import co.gov.metropol.area247.services.rest.metro.FeatureCivicaExpedicionWSDTO;

public interface ICivicaServiceGateway {
	
	/**
	 * Obtener la información de los puntos de expedición de tarjeta civica<br>
	 * Creado el 26/12/2017 a las  4:34:37 p. m. <br>
	 * @return lista de objetos {@link FeatureCivicaExpedicionWSDTO}
	 */
	List<FeatureCivicaExpedicionWSDTO> consultarCivicaExpedicion();
}
