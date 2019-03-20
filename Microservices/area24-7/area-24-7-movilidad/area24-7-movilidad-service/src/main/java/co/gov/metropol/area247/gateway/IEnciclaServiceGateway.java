package co.gov.metropol.area247.gateway;


import java.util.List;

import co.gov.metropol.area247.services.rest.encicla.EnciclaWSDTO;
import co.gov.metropol.area247.services.rest.encicla.FeatureWSDTO;

public interface IEnciclaServiceGateway {
	
	/**
	 * Obtener informacion del servicio de encilca para que este se almacene en la base de datos.
	 * @return EnciclaWSDTO
	 */
	EnciclaWSDTO consultarEstatusEncicla();
	
	/**
	 * Obtener informacion del servicio de cicloRutas para que este se almacene en la base de datos.
	 * @return CicloRutaWSDTO
	 */
	List<FeatureWSDTO> consultarCicloRutas();

}
