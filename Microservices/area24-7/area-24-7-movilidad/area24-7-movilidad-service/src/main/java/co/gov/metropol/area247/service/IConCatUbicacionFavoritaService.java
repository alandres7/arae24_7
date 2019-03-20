package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.ConCatUbicacionFavoritaDTO;

public interface IConCatUbicacionFavoritaService {

	/**
	 * Busca una categoria de ubicacion favorita segun el identificafdor
	 * 
	 * @param id
	 *            - filtro de busqueda
	 * 
	 * @return {@link ConCatUbicacionFavoritaDTO}
	 */
	ConCatUbicacionFavoritaDTO findById(Long id);
	
}
