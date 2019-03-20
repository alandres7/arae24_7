package co.gov.metropol.area247.gateway;

import co.gov.metropol.area247.services.rest.metro.MetroWSDTO;

public interface IMetroServiceGateway {

	/**
	 * Obtener informacion del servicio del metro para que este se almacene en la base de datos.
	 * 
	 * @return MetroWSDTO
	 * */
	MetroWSDTO consultarDatosMetro();
	
}
