package co.gov.metropol.area247.gateway;

import java.util.Date;

import co.gov.metropol.area247.services.rest.siata.SiataWSDTO;
import co.gov.metropol.area247.util.web.Coordenada;

public interface ISiataServiceGateway {

	/**
	 * Obtener la información del pronóstico de precipitación y estado del tiempo en
	 * las ubicaciones origen y destino establecidas
	 * 
	 * @param fecha
	 *            - fecha de solicitud del servicio
	 * @param fechaOrigen
	 *            - fecha origen
	 * @param coordenadaOrigen
	 *            - latitud y longitud donde se encuentra parado el usuario.
	 * @param fechaDestino
	 *            - fecha destino
	 * @param coordenadaDestino
	 *            - longitud y latitud donde se dirige el usuario.
	 * @return SiataWSDTO - SiataWSDTO
	 */
	SiataWSDTO consultarPronostico(Date fecha, Date fechaOrigen, Coordenada coordenadaOrigen, Date fechaDestino,
			Coordenada coordenadaDestino);

}
