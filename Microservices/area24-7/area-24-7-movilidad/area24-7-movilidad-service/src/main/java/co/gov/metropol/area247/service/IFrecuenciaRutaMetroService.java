package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.FrecuenciaRutaMetroDTO;

public interface IFrecuenciaRutaMetroService {

	/**
	 * Buscar la frecuencia de la ruta del metro dado el identificador de esta
	 * 
	 * @param idFrecuencia
	 *            - identificador de la frecuencia dado por el metro
	 * @return - {@link FrecuenciaRutaMetroDTO}
	 */
	FrecuenciaRutaMetroDTO findByFrecuenciaRutaId(Long idFrecuencia);

	/**
	 * Guardado de la frecuencia de la ruta del metro.
	 * 
	 * @param frecuenciaRutaMetroDTO
	 *            - {@link FrecuenciaRutaMetroDTO}
	 */
	void saveFrecuenciaRuta(FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO);

	/**
	 * Actualizar la informacion de la frecuencia de la ruta proporcionada por el
	 * servicio del metro
	 * 
	 * @param frecuenciaRutaMetroDTO
	 *            - {@link FrecuenciaRutaMetroDTO}
	 */
	void updateFrecuenciaRuta(FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO);

	/**
	 * Buscar una frecuencia de la ruta dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico de la frecuencia de la ruta.
	 * @return - {@link FrecuenciaRutaMetroDTO}}
	 */
	FrecuenciaRutaMetroDTO findById(Long id);

	/**
	 * Guardar o actualizar todas las frecuencias de las rutas del metro pasadas
	 * como argumentos
	 * 
	 * @param frecuenciasRutaMetroDTO
	 *            - lista de frecuencias de rutas del metro
	 */
	void procesarFrecuencias(List<FrecuenciaRutaMetroDTO> frecuenciasRutaMetroDTO);

	/**
	 * Guarda o actualiza la frecuencia
	 * 
	 * @param frecuenciaRutaMetroDTO
	 *            - objeto a procesar
	 */
	void procesarFrecuencia(FrecuenciaRutaMetroDTO frecuenciaRutaMetroDTO);

	/**
	 * Buscar las frecuencias de la linea que coincida con el identificador de la
	 * linea fijado como parametro.
	 * 
	 * @param idRuta
	 *            - filtro de busqueda
	 * 
	 * @return una lista de objetos {@link FrecuenciaRutaMetroDTO}
	 */
	List<FrecuenciaRutaMetroDTO> findByIdRuta(Long idRuta);

}
