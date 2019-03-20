package co.gov.metropol.area247.centrocontrol.carga.gateway;

import co.gov.metropol.area247.centrocontrol.dto.CirculacionVehiculoWSDTO;

public interface ICirculacionVehiculoGateway {
	
	/**
	 * Obtiene el objeto JSON con la información de circulación de vehiculos de carga
	 * @return CirculacionVehiculoWSDTO 
	 */
	CirculacionVehiculoWSDTO cargarCiculacionVeiculos();

}
