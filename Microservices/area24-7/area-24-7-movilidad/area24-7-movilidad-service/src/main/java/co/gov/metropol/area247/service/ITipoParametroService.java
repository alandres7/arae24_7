package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.TipoParametroDTO;

public interface ITipoParametroService {

	/**
	 * Busca parametro por su identificador unico
	 * 
	 * @param idParametro - identificador unico
	 * 
	 * @return un parametro tipo {@link TipoParametroDTO}
	 */
	TipoParametroDTO findById(Long idParametro);
	
	/**
	 * Obtiene el valor parametro convertido a valor un numerico
	 * 
	 * @param idParametro
	 *            - clave para encontrar el valor parametro
	 * @return un valor de tipo {@link Long}
	 */
	Long obtenerValorEntero(Long idParametro);
	
	/**
	 * Obtiene el valor parametro
	 * 
	 * @param idParametro
	 *            - clave para encontrar el valor parametro
	 * @return un valor de tipo {@link String}
	 */
	String obtenerValorCadena(Long idParametro);
	
}
