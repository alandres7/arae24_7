package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.TipoRutaDTO;

public interface ITipoRutaService {

	/**
	 * Busca un tipo de ruta que coincida con los argumentos definidos
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda, posibles valores (1, ENCICLA), (2,
	 *            GTPC), (3, METRO)
	 * @return un objeto de tipo {@link TipoRutaDTO}
	 */
	TipoRutaDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);

	/**
	 * Guardado del tipo de ruta.
	 * 
	 * @param tipoRutaDTO
	 *            - {@link TipoRutaDTO}
	 */
	void saveTipoRuta(TipoRutaDTO tipoRutaDTO);

	/**
	 * Actualizar la informacion del tipo de la ruta
	 * 
	 * @param tipoRutaDTO
	 *            - {@link TipoRutaDTO}
	 */
	void updateTipoRuta(TipoRutaDTO tipoRutaDTO);

	/**
	 * Guardar o actualizar el tipo de ruta definido como parametro
	 * 
	 * @param tipoRutaDTO
	 *            - objeto a procesar, ya sea para guardarlo como nuevo o para
	 *            actualizarlo
	 */
	void procesarTipoRuta(TipoRutaDTO tipoRutaDTO);
	
	/**
	 * Busca el registro que cumpla con el criterio de busqueda
	 * 
	 * @param id
	 *            - filtro de busqueda
	 * @return {@link TipoRutaDTO}
	 */
	TipoRutaDTO findById(Long id);

}
