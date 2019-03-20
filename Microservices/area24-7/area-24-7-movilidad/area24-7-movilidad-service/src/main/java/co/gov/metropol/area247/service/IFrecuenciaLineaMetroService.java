package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;
import co.gov.metropol.area247.repository.domain.FrecuenciaLinea;

public interface IFrecuenciaLineaMetroService {

	/**
	 * Buscar la frecuencia de la linea del metro dado el identificador de esta
	 * 
	 * @param idFrecuencia
	 *            - identificador de la frecuencia dado por el metro
	 * @return FrecuenciaLineaMetroDTO - {@link FrecuenciaLineaMetroDTO}
	 */
	FrecuenciaLineaMetroDTO findByFrecuenciaLineaId(Long idFrecuencia);

	/**
	 * Guardado de la frecuencia de la linea del metro.
	 * 
	 * @param frecuenciaLineaMetroDTO
	 *            - {@link FrecuenciaLineaMetroDTO}
	 * @return FrecuenciaLinea
	 */
	FrecuenciaLinea saveFrecuenciaLinea(FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO);

	/**
	 * Actualizar la informacion de la frecuencia de la linea proporcionada por
	 * el servicio del metro
	 * 
	 * @param frecuenciaLineaMetroDTO
	 *            - {@link FrecuenciaLineaMetroDTO}
	 * 
	 * @return FrecuenciaLinea
	 */
	FrecuenciaLinea updateFrecuenciaLinea(FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO);

	/**
	 * Buscar una frecuencia de la linea dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico de la frecuencia de la linea.
	 * @return FrecuenciaLineaMetroDTO - {@link FrecuenciaLineaMetroDTO}}
	 */
	FrecuenciaLineaMetroDTO findById(Long id);

	/**
	 * Guardar o actualizar todas las frecuencias de las lineas del metro
	 * pasadas como argumentos
	 * 
	 * @param frecuenciasLineaMetroDTO
	 *            - lista de frecuencias de lineas del metro
	 */
	void procesarFrecuencias(List<FrecuenciaLineaMetroDTO> frecuenciasLineaMetroDTO);

	/**
	 * Guarda o actualiza una frecuencia de la linea
	 * 
	 * @param frecuenciaLineaMetroDTO
	 *            - objeto a persistir
	 *            
	 * @return {@link FrecuenciaLinea}
	 */
	FrecuenciaLinea procesarFrecuencia(FrecuenciaLineaMetroDTO frecuenciaLineaMetroDTO);

	/**
	 * Buscar las frecuencias de la linea que coincida con el identificador de
	 * la linea fijado como parametro.
	 * 
	 * @param idLinea
	 *            - filtro de busqueda
	 * 
	 * @param m2o
	 *            - (ManyToOne) <code>true</code> para obtener la informacion de
	 *            sus llaves foraneas
	 * 
	 * @return una lista de objetos de tipo {@link FrecuenciaLineaMetroDTO}
	 */
	List<FrecuenciaLineaMetroDTO> findByIdLinea(Long idLinea, boolean m2o);

}
