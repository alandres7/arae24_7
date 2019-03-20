package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.TareviaEstacionMetroDTO;
import co.gov.metropol.area247.repository.domain.LineaMetro;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.util.web.Coordenada;

public interface ILineaMetroService {

	/**
	 * Buscar la linea del metro dado el identificador de esta
	 * 
	 * @param idLinea
	 *            - identificador dado por el metro
	 * 
	 * @return - {@link LineaMetroDTO}
	 */
	LineaMetroDTO findByLineaMetroId(Long idLinea);

	/**
	 * Guardado de la linea del metro.
	 * 
	 * @param lineaMetroDTO
	 *            - {@link LineaMetroDTO}
	 * 
	 * @return la entidad LineaMetro
	 */
	LineaMetro saveLineaMetro(LineaMetroDTO lineaMetroDTO);

	/**
	 * Actualizar la informacion de la linea proporcionada por el servicio del metro
	 * 
	 * @param lineaMetroDTO
	 *            - {@link LineaMetroDTO}
	 * 
	 * @return la entidad LineaMetro
	 */
	LineaMetro updateLineaMetro(LineaMetroDTO lineaMetroDTO);

	/**
	 * Buscar una linea dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico de la linea.
	 * 
	 * @param m2o
	 *            - (ManyToOne) <code>true</code> para obtener la informacion de
	 *            sus llaves foraneas
	 * 
	 * @param o2m
	 *            - (OneToMany) <code>true</code> para obtener la informacion de
	 *            las listas, registros secundarios de la linea, como por
	 *            ejemplo estaciones, horarios y frecuencias
	 * 
	 * @return lineaMetroDTO - {@link LineaMetroDTO}
	 */
	LineaMetroDTO findById(Long id, boolean m2o, boolean o2m);

	/**
	 * Guardar o actualizar todas las lineas del metro pasadas como argumentos
	 * 
	 * @param lineasMetroDTO
	 *            - las lineas del metro a guardar o actualizar en caso de que ya
	 *            sean existentes
	 */
	void procesarLineas(List<LineaMetroDTO> lineasMetroDTO);

	/**
	 * Guarda o actualiza la linea del metro
	 * 
	 * @param lineaMetroDTO
	 *            - objeto a procesar
	 * 
	 * @return la entidad LineaMetro
	 */
	LineaMetro procesarLinea(LineaMetroDTO lineaMetroDTO);

	/**
	 * Obtiene la informacion de las lineas por medio de su codigo o nombre
	 * 
	 * @param parametro
	 *            - codigo o nombre de la linea
	 * @return lista de LineaMetroDTO
	 */
	List<LineaMetroDTO> findByCodigoOrDescripcion(String parametro);

	/**
	 * Obtiene la informacion de la linea metro que coincida con el codigo fijado en
	 * el argumento.
	 * 
	 * @param codigo
	 *            - filtro de busqueda
	 * 
	 * @return {@link LineaMetroDTO}
	 */
	LineaMetroDTO findByCodigo(String codigo);

	/**
	 * 
	 * @param latitud
	 *            - latitud
	 * @param longitud
	 *            - longitud
	 * @param radio
	 *            - radio
	 * @return lista de tipo TareviaEstacionMetroDTO
	 */
	List<TareviaEstacionMetroDTO> obtenerEstacionesMetroCercanas(Double latitud, Double longitud, Float radio);
	
	
	/**
	 * Obtiene las lineas que pasan por un radio dado de un punto dado
	 * 
	 * @param coordenada
	 *            - contiene la latitud, longitud y el radio
	 * @param modosTransporte
	 *            - modos de transporte ej, cable, tranvia, metro, etc.
	 * @return las lineas encontradas
	 */
	List<LineaMetroDTO> obtenerlineasCercanas(Coordenada coordenada, List<ModoRecorrido> modosTransporte);
	
	/**
	 * Obtiene la informacion completa de las llaves foraneas
	 * 
	 * @param lineaMetroDTO
	 *            - donde se fijara la informacion de las llaves
	 */
	public void conProfundizacion(LineaMetroDTO lineaMetroDTO);

	/**
	 * Obtiene la informacion de listas secundarias
	 * 
	 * @param lineaMetroDTO
	 *            - donde se fijara la informacion de las listas secundarias
	 */
	public void conListasSecundarias(LineaMetroDTO lineaMetroDTO);
	
	/**
	 * Obtiene la informacion de las frecuencias de la linea indicada como
	 * argumento y fija a este mismo objeto esta informacion.
	 * 
	 * @param lineaMetroDTO
	 *            - funciona como filtro de busqueda(el id no puede ser null) y
	 *            estas frecuencias encontradas se fijan a este.
	 */
	public void conFrecuencias(LineaMetroDTO lineaMetroDTO);
	
	/**
	 * Obtiene la informacion de los horarios de la linea indicada como
	 * argumento y fija a este mismo objeto esta informacion.
	 * 
	 * @param lineaMetroDTO
	 *            - funciona como filtro de busqueda(el id no puede ser null) y
	 *            estos horarios encontradas se fijan a este.
	 */
	public void conHorarios(LineaMetroDTO lineaMetroDTO);
	
	/**
	 * Obtiene la informacion de las estaciones de la linea indicada como
	 * argumento y fija a este mismo objeto esta informacion.
	 * 
	 * @param lineaMetroDTO
	 *            - funciona como filtro de busqueda(el id no puede ser null) y
	 *            estas estaciones encontradas se fijan a este.
	 */
	public void conEstaciones(LineaMetroDTO lineaMetroDTO);
	
	/**
	 * Obtiene todas las lineas del metro activas
	 * Creado 16/07/2018 02:28:10 p.m
	 * @return conjunto de todas las lineas del metro
	 */
	List<LineaMetroDTO> findAllActivas();
	
}
