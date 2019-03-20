package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.CiclorutaDTO;
import co.gov.metropol.area247.model.DareviaZonaDTO;
import co.gov.metropol.area247.model.DisponibilidadCiclaDTO;
import co.gov.metropol.area247.model.EstacionEnciclaDTO;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;

public interface IEnciclaService {

	/**
	 * Cargar la informacion del servicio de encicla en la base de datos
	 */
	void cargarEstacionesEncicla();
	
	/**
	 * Cargar la informacion del servicio de cicloRutas en la base de datos
	 */
	void cargarCicloRutas();

	/**
	 * Buscar la estacion dado el identificador de la estacion de encicla
	 * 
	 * @param idEstacion
	 *            - identificador dado por encicla
	 * @return EstacionEnciclaDTO
	 */
	EstacionEnciclaDTO findByEstacionId(Long idEstacion);

	/**
	 * Buscar la estacion dado el identificador de la estacion de encicla
	 * 
	 * @param idEstacion
	 *            - identificador dado por encicla
	 * @return DisponibilidadCiclaDTO
	 */
	DisponibilidadCiclaDTO findAvailabilityByEstacionId(Long idEstacion);

	/**
	 * Buscar la estacion dado el identificador de la estacion de encicla
	 * 
	 * @param idEstacionEncicla
	 *            - identificador dado por encicla
	 * @return TareviaEstacionEnciclaDTO
	 */
	TareviaEstacionEnciclaDTO findByEstacionEnciclaId(Long idEstacionEncicla);

	/**
	 * Guardado de la estacion de encicla.
	 * 
	 * @param tareviaEstacionEncicla
	 *            - {@link TareviaEstacionEnciclaDTO}
	 */
	void saveEstacionEncicla(TareviaEstacionEnciclaDTO tareviaEstacionEncicla);

	/**
	 * Actualizar la informacion de la estacion proporcionada por el servicio de
	 * encicla
	 * 
	 * @param tareviaEstacionEnciclaDTO - tareviaEstacionEnciclaDTO 
	 * @param ultimaConsulta
	 *            - {@link TareviaEstacionEnciclaDTO}
	 */
	void updateEstacionEncicla(TareviaEstacionEnciclaDTO tareviaEstacionEnciclaDTO, Long ultimaConsulta);

	/**
	 * Obtener las estaciones de encicla dada las coordenadas de un usuario
	 * 
	 * @param latitud
	 *            - latitud donde se encuentra parado el usuario.
	 * @param longitud
	 *            - longitud donde se encuentra parado el usuario
	 * @param radio
	 *            - radio de busqueda designado por el usuario
	 * @return List type TareviaEstacionEnciclaDTO
	 */
	List<TareviaEstacionEnciclaDTO> obtenerEstacionesPorProximidad(Double latitud, Double longitud, Float radio);

	/**
	 * Buscar una estacion dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico de la estacion.
	 * @return EstacionEnciclaDTO
	 */
	EstacionEnciclaDTO findById(Long id);

	/**
	 * Buscar la zona dado el identificador de la zona de encicla
	 * 
	 * @param idZona
	 *            - identificador dado por encicla
	 * @return DareviaZonaDTO
	 */
	DareviaZonaDTO findByZonaId(Long idZona);

	/**
	 * Guardado de la zona de encicla.
	 * 
	 * @param dareviaZonaDTO
	 *            - {@link DareviaZonaDTO}
	 */
	void saveZonaEncicla(DareviaZonaDTO dareviaZonaDTO);

	/**
	 * Actualizar la informacion de la zona proporcionada por el servicio de encicla
	 * 
	 * @param dareviaZonaDTO
	 *            - {@link DareviaZonaDTO}
	 * @param ultimaConsulta - ultimaConsulta 
	 */
	void updateZonaEncicla(DareviaZonaDTO dareviaZonaDTO, Long ultimaConsulta);

	/**
	 * Obtener las estaciones de encicla dada las coordenadas de un usuario
	 * 
	 * @param latitud
	 *            - latitud donde se encuentra parado el usuario.
	 * @param longitud
	 *            - longitud donde se encuentra parado el usuario
	 * @param radio
	 *            - radio de busqueda designado por el usuario
	 * @return List type TareviaEstacionEnciclaDTO
	 */
	List<TareviaEstacionEnciclaDTO> obtenerEstacionesCercanas(Double latitud, Double longitud, Double radio);

	/**
	 * Obtiene la disponibilidad de bicicletas de una estacion en especifico
	 * @param id - id
	 * @return DisponibilidadCiclaDTO
	 */
	DisponibilidadCiclaDTO disponibilidadEstacionEncicla(Long id);
	
	/**
	 * Obtiene las ciclorutas que esten dentro o pasan por el punto y radio
	 * fijado
	 * 
	 * @param latitud
	 *            - latitud
	 * @param longitud
	 *            - longitud
	 * @param radio
	 *            - radio
	 * 
	 * @return lista de tipo CiclorutaDTO
	 */
	List<CiclorutaDTO> obtenerCiclorutasCercanas(Double latitud, Double longitud, Double radio);
	
	/**
	 * Se obtiene la cicloruta por el idItem
	 * Creado el 22/11/2017 a las  8:35:53 p. m.
	 * @param idItem - idItem
	 * @return CiclorutaDTO
	 */
	CiclorutaDTO findCicloRutaById(Long idItem);
	
	/**
	 * Inserta un regitro en la tabla CicloRuta
	 * Creado el 22/11/2017 a las  8:49:29 p. m. <br>
	 * @param cicloRutaDTO - entidad a persistir
	 */
	void saveCicloRuta(CiclorutaDTO cicloRutaDTO);

	/**
	 * Actualiza un registro de la tabla cicloRuta
	 * Creado el 22/11/2017 a las  8:49:13 p. m. <br>
	 * @param ciclorutaDTO - entidad a actualizar
	 */
	void updateCicloRura(CiclorutaDTO ciclorutaDTO);
}
