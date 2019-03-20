package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.TipoLineaDTO;

public interface ITipoLineaService {

	/**
	 * Realiza busqueda por el campo idTipoLinea
	 * 
	 * @param id
	 *            - identificador que obtiene desde el servicio web
	 * 
	 * @return objeto TipoLineaDTO encontrado, o null si no lo encuentra
	 */
	TipoLineaDTO findById(Long id);

	/**
	 * Guardado del tipo de linea.
	 * @param tipoLineaDTO
	 *            - {@link TipoLineaDTO}
	 */
	void saveTipoLinea(TipoLineaDTO tipoLineaDTO);

	/**
	 * Actualizar la informacion del tipo de la linea
	 * 
	 * @param tipoLineaDTO
	 *            - {@link TipoLineaDTO}
	 */
	void updateTipoLinea(TipoLineaDTO tipoLineaDTO);

	/**
	 * Guardar o actualizar el tipo de linea definido como parametro
	 * 
	 * @param tipoLineaDTO
	 *            - objeto a procesar, ya sea para guardarlo como nuevo o para
	 *            actualizarlo
	 */
	void procesarTipoLinea(TipoLineaDTO tipoLineaDTO);
	
	/**
	 * Realiza busqueda por el campo nombre
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * 
	 * @return objeto TipoLineaDTO encontrado, o null si no lo encuentra
	 */
	TipoLineaDTO findByNombre(String nombre);

}
