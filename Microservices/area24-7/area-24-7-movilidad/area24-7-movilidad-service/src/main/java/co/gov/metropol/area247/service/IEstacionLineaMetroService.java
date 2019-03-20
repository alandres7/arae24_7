package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.util.web.Coordenada;

public interface IEstacionLineaMetroService {

	/**
	 * Buscar la estacion de la linea del metro dado el identificador de esta
	 * 
	 * @param idEstacion
	 *            - identificador de la estacion dado por el metro
	 * 
	 * @return - {@link EstacionLineaMetroDTO}
	 */
	EstacionLineaMetroDTO findByEstacionLineaId(Long idEstacion);

	/**
	 * Guardado de la estacion de la linea del metro.
	 * 
	 * @param estacionLineaMetroDTO
	 *            - {@link EstacionLineaMetroDTO}
	 */
	void saveEstacionMetro(EstacionLineaMetroDTO estacionLineaMetroDTO);

	/**
	 * Actualizar la informacion de la estacion de la linea proporcionada por el
	 * servicio del metro
	 * 
	 * @param estacionLineaMetroDTO
	 *            - {@link EstacionLineaMetroDTO}
	 */
	void updateEstacionMetro(EstacionLineaMetroDTO estacionLineaMetroDTO);

	/**
	 * Buscar una estacion de la linea dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico de la estacion de la linea.
	 * @return - {@link EstacionLineaMetroDTO}}
	 */
	EstacionLineaMetroDTO findById(Long id);

	/**
	 * Guardar o actualizar todas las estaciones de las lineas del metro pasadas
	 * como argumentos
	 * 
	 * @param estacionesLineaMetroDTO
	 *            - lista de estaciones de lineas del metro
	 */
	void procesarEstaciones(List<EstacionLineaMetroDTO> estacionesLineaMetroDTO);

	/**
	 * Guarda o actualiza la estacion
	 * 
	 * @param estacionLineaMetroDTO
	 *            - objeto a procesar
	 */
	void procesarEstacion(EstacionLineaMetroDTO estacionLineaMetroDTO);

	/**
	 * Buscar la estacion de la linea del metro dado el codigo y el id de la
	 * linea
	 * 
	 * @param codigo
	 *            - codigo de la estacion dado por el metro
	 * @param idLinea
	 *            - id de la linea especifica para que no obtenga mas de una
	 *            estacion
	 * 
	 * @return - {@link EstacionLineaMetroDTO}
	 */
	EstacionLineaMetroDTO findByCodigoAndIdLinea(String codigo, Long idLinea);
	
	/**
	 * Obtiene las estaciones de las lineas definidas mas cercanas al punto de
	 * coordenada dado.
	 * 
	 * @param coordenada
	 *            - contiene la latitud, longitud y radio a abarcar
	 * @param modosRecorrido
	 *            - contiene los modos de recorrido de las lineas para filtrar las
	 *            estaciones
	 * @return una lista de objetos tipo {@link EstacionLineaMetroDTO}
	 */
	List<EstacionLineaMetroDTO> obtenerEstacionesCercanas(Coordenada coordenada, List<Long> modosRecorrido);

	/**
	 * Obtener las estaciones por donde pasa una linea de metro
	 * 
	 * @param idLinea
	 *            - identificador unico de la linea, filtro de busqueda
	 * @param profundo
	 *            - <code>true</code> indica si trae la informacion completa de
	 *            las llaves foraneas ej. LineaDTO. <code>false</code> solo trae
	 *            el id de las llaves foraneas.
	 * 
	 * @return las estaciones que cumplan con los criterios de busqueda
	 */
	List<EstacionLineaMetroDTO> findByIdLinea(Long idLinea, boolean profundo);
	
	/**
	 * Obtiene todas las estaciones del metro activas
	 * Creado 24/07/2018 
	 * @return todas las estaciones del metro activas
	 */
	List<EstacionLineaMetroDTO> getAllActivas();
}
