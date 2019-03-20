package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.TipoModoTransporteDTO;

public interface ITipoModoTransporteService {

	/**
	 * Busca el tipo modo de transporte que coincida con el identificador unico
	 * 
	 * @param id
	 *            - filtro de busqueda
	 * @return un objeto {@link TipoModoTransporteDTO}
	 */
	TipoModoTransporteDTO findById(Long id);
	
	/**
	 * Obtiene un tipo modo de transporte que coincida con los filtros de
	 * busqueda
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda
	 * 
	 * @return - {@link TipoModoTransporteDTO}
	 */
	TipoModoTransporteDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);

	/**
	 * Persiste un nuevo tipo de modo de transporte a la BD
	 * 
	 * @param tipoModoTransporteDTO
	 *            - tipo de modo de transporte a persistir
	 */
	void saveTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO);

	/**
	 * Actualizar un nuevo tipo de modo de transporte a la BD
	 * 
	 * @param tipoModoTransporteDTO
	 *            - tipo de modo de transporte a actualizar
	 */
	void updateTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO);

	/**
	 * Valida el objeto para luego persistir o actualizar
	 * 
	 * @param tipoModoTransporteDTO
	 *            - objeto a procesar
	 */
	void procesarTipoModoTransporte(TipoModoTransporteDTO tipoModoTransporteDTO);

}
