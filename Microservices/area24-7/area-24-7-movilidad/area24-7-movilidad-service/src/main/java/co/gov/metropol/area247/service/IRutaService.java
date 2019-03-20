package co.gov.metropol.area247.service;

import java.util.List;
import java.util.Map;

import co.gov.metropol.area247.model.CiclorutaDTO;
import co.gov.metropol.area247.model.ParaderoRutaDTO;
import co.gov.metropol.area247.model.PuntoTarjetaCivicaDTO;
import co.gov.metropol.area247.model.RutaConcretaDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ClasificacionInformacion;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.util.web.Coordenada;

public interface IRutaService {
	
	/**
	 * Obtener las estaciones de encicla mas cercanas
	 * @param coordenada - coordenada
	 * @return lista de tipo TareviaEstacionEnciclaDTO
	 */
	List<TareviaEstacionEnciclaDTO> obtenerEstacionesCercanas(Coordenada coordenada);
	
	/**
	 * Obtener las ciclorutas mas cercanas
	 * @param coordenada - coordenada
	 * @return lista de tipo CiclorutaDTO
	 */
	List<CiclorutaDTO> obtenerCiclorutasCercanas(Coordenada coordenada);
		
	/**
	 * 
	 * @param idRuta - nombre o codigo
	 * @return lista de tipo ParaderoRutaDTO
	 */
	public List<ParaderoRutaDTO> obtenerParaderos(Long idRuta);
	
	/**
	 * obtener las rutas por nombre o descripcion
	 * @param parametro - nombre o descripcion
	 * @return lista de Rutas
	 */
	List<RutaMetroDTO> findByCodigoOrDescripcion(String parametro);
	
	/**
	 * Obtener los puntos de tarjeta civica mas cercanos
	 * @param coordenada - coordenada
	 * @return lista de tipo PuntoTarjetaCivicaDTO
	 */
	List<PuntoTarjetaCivicaDTO> obtenerPuntosTarjetaCivicaCercanos(Coordenada coordenada);

	/**
	 * Obtiene la informacion de rutas(autobus, alimentadores, integrados, metro,
	 * tranvia, etc), estaciones(rutas, lineas y encicla) y puntos de tarjeta
	 * civica que esten dentro de un radio dado de un punto de coordenada dado.
	 * 
	 * @param coordenada
	 *            - contiene la latitud, longitud y el radio
	 * @param modosTransporte
	 *            - modos de transporte ej, cable, tranvia, metro, integrado,
	 *            etc.
	 * 
	 * @return un mapa con la informacion descrita anteriormente.
	 */
	Map<ClasificacionInformacion, List<Object>> obtenerInformacionTransporteCercano(Coordenada coordenada,
			List<Integer> modosTransporte);

	/**
	 * Obtiene las rutas de un modo de transporte dado que pasen por un radio
	 * dado de un punto de origen tambien dado.
	 * 
	 * @param coordenada
	 *            - contiene latitud, longitud y radio del punto de origen
	 * @param modosTransporte
	 *            - modos de transportarse, ej. metro plus, alimentador, etc.
	 *            
	 * @return una lista de objetos de tipo {@link RutaMetroDTO}
	 */
	List<RutaMetroDTO> obtenerRutasCercanas(Coordenada coordenada, List<ModoRecorrido> modosTransporte);

	/**
	 * Obtiene el informacion como el id, codigo, descripcion y tipo de ruta (si
	 * es ruta o linea) de las lineas o rutas.
	 * 
	 * @param parametro - filtro de busqueda
	 * 
	 * @return una lista con la informacion de las lineas y rutas
	 */
	List<RutaConcretaDTO> findInfoLineasAndRutasByCodigoOrDescripcion(String parametro);
	
	/**
	 * Obtiene toda la informacion de una ruta por el id
	 * 
	 * @param id - filtro de busqueda
	 * 
	 * @param m2o
	 *            - (ManyToOne) <code>true</code> para obtener la informacion de
	 *            sus llaves foraneas
	 * 
	 * @param o2m
	 *            - (OneToMany) <code>true</code> para obtener la informacion de
	 *            las listas, registros secundarios de la ruta, como por
	 *            ejemplo estaciones, horarios y frecuencias
	 * 
	 * 
	 * @return ruta que coincida con el filtro de busqueda
	 */
	RutaMetroDTO findById(Long id, boolean m2o, boolean o2m);
	
}
