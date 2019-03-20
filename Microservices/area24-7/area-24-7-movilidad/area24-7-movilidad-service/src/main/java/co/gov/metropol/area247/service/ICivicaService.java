package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.CivicaDTO;

public interface ICivicaService {

	/**
	 * Obtiene los puntos de expedición de la tarjeta civica
	 * desde un servicio web
	 * y los almacena en la base de datos
	 * Creado el 27/12/2017 a las  8:44:09 a. m. <br>
	 */
	void cargarPutosCivicaExpedicion();
	
	/**
	 * Retorna un registro de la tabla Tarjeta_Civica por su id
	 * Creado el 27/12/2017 a las  2:28:04 p. m. <br>
	 * @param idItem - filtro de busqueda
	 * @param tipoPunto - filtro de busqueda
	 * @return CivicaDTO
	 */
	CivicaDTO findCivicaByIdItemAndTipoPunto(Long idItem, String tipoPunto);
	
	/**
	* Almacena un registro en la tabla Tarjeta_Civica
	 * Creado el 27/12/2017 a las  2:28:04 p. m. <br>
	 * @param civicaDTO - entidad a persistir
	 */
	void saveCivica(CivicaDTO civicaDTO);
	
	/**
	* Actualiza un registro en la tabla Tarjeta_Civica
	 * Creado el 27/12/2017 a las  2:28:04 p. m. <br>
	 * @param civicaDTO - entidad a actualizar
	 */
	void updateCivica(CivicaDTO civicaDTO);
}
