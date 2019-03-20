package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.ConCatUbicacionFavoritaDTO;

public interface ICatUbicacionFavoritaService {

	/**
	 * Obtiene todas las categorias de las ubicaciones favoritas
	 * 
	 * @return una lista de objetos {@link ConCatUbicacionFavoritaDTO}
	 */
	List<ConCatUbicacionFavoritaDTO> getAll();

}
