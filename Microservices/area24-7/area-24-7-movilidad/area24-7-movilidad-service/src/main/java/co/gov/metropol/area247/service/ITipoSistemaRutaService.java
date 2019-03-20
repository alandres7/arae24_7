package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.TipoSistemaRutaDTO;

public interface ITipoSistemaRutaService {

	/**
	 * Busca un tipo de sistema de la ruta que coincida con los argumentos definidos
	 * 
	 * @param nombre
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda, posibles valores (1, ENCICLA), (2,
	 *            GTPC), (3, METRO)
	 * @return un objeto de tipo {@link TipoSistemaRutaDTO}
	 */
	TipoSistemaRutaDTO findByNombreAndFuenteDatos(String nombre, Integer fuenteDatos);

	/**
	 * Guardado del tipo de sistema de la ruta.
	 * @param tipoSistemaRutaDTO
	 *            - {@link TipoSistemaRutaDTO}
	 */
	void saveTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO);

	/**
	 * Actualizar la informacion del tipo de sistema de la ruta
	 * 
	 * @param tipoSistemaRutaDTO
	 *            - {@link TipoSistemaRutaDTO}
	 */
	void updateTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO);

	/**
	 * Guardar o actualizar el tipo de sistema de la ruta definido como parametro
	 * 
	 * @param tipoSistemaRutaDTO
	 *            - objeto a procesar, ya sea para guardarlo como nuevo o para
	 *            actualizarlo
	 */
	void procesarTipoSistemaRuta(TipoSistemaRutaDTO tipoSistemaRutaDTO);
	
	/**
	 * Realiza la busqueda del tipo de sistema de ruta que coincida con el identificador unico
	 * 
	 * @param id - filtro de busqueda
	 * 
	 * @return un objeto {@link TipoSistemaRutaDTO}
	 */
	TipoSistemaRutaDTO findById(Long id);
	
	/**
	 * Obtiene los tipos de sistemas activos
	 * <P>
	 * Creado 2/08/2018 4:47 p.m
	 * @return una lista de objetos tipo {@link TipoSistemaRutaDTO}
	 */
	List<TipoSistemaRutaDTO> findAllActivas();
	
}
