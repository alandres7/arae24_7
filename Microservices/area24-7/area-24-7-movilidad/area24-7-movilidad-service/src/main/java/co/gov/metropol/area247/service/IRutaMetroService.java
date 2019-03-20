package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.RutaMetroDTO;

public interface IRutaMetroService {
	
	/**
	 * Buscar la ruta del metro dado el identificador de esta
	 * 
	 * @param idRuta
	 *            - identificador dado por el metro
	 * 
	 * @return - {@link RutaMetroDTO}
	 */
	RutaMetroDTO findByRutaMetroId(Long idRuta);

	/**
	 * Guardado de la ruta del metro.
	 * 
	 * @param rutaMetroDTO
	 *            - {@link RutaMetroDTO}
	 */
	void saveRuta(RutaMetroDTO rutaMetroDTO);

	/**
	 * Actualizar la informacion de la ruta proporcionada por el servicio del
	 * metro
	 * 
	 * @param rutaMetroDTO
	 *            - {@link RutaMetroDTO}
	 */
	void updateRuta(RutaMetroDTO rutaMetroDTO);

	/**
	 * Buscar una ruta dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico de la ruta.
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
	 * @return rutaMetroDTO - {@link RutaMetroDTO}
	 */
	RutaMetroDTO findById(Long id, boolean m2o, boolean o2m);

	/**
	 * Guardar o actualizar todas las rutas del metro pasadas como argumentos
	 * 
	 * @param rutasMetroDTO
	 *            - las rutas del metro a guardar o actualizar en caso de que ya
	 *            sean existentes
	 */
	void procesarRutas(List<RutaMetroDTO> rutasMetroDTO);

	/**
	 * Guarda o actualiza la ruta del metro
	 * 
	 * @param rutaMetroDTO
	 *            - objeto a procesar
	 */
	void procesarRuta(RutaMetroDTO rutaMetroDTO);

	/**
	 * Busca la ruta del metro que coincida con los argumentos definidos
	 * 
	 * @param idItem
	 *            - identificador que viene desde el servicio del metro
	 * @param fuenteDatos
	 *            - filtro de la fuente de datos (1, ENCICLA), (2, GTPC), (3,
	 *            METRO)
	 * @return un objeto de tipo {@link RutaMetroDTO}
	 */
	RutaMetroDTO findByIdItemAndFuenteDatos(Long idItem, Integer fuenteDatos);

	/**
	 * Busca la ruta que coincida con los argumentos definidos
	 * 
	 * @param codigo
	 *            - filtro de busqueda (obligatorio).
	 * @param fuenteDatos
	 *            - filtro de busqueda (obligatorio).
	 * 
	 * @return {@link RutaMetroDTO}
	 */
	RutaMetroDTO findByCodigoAndFuenteDatos(String codigo, Integer fuenteDatos);
	
	/**
	 * Busca por el codigo de la ruta
	 * 
	 * @param codigo - filtro de busqueda
	 * 
	 * @return {@link RutaMetroDTO}
	 */
	RutaMetroDTO findByCodigo(String codigo);
	
	/**
	 * Obtiene la informacion completa de las llaves foraneas
	 * 
	 * @param rutaMetroDTO
	 *            - donde se fijara la informacion de las llaves
	 */
	void conProfundizacion(RutaMetroDTO rutaMetroDTO); 
	
	/**
	 * Obtiene la informacion de listas secundarias
	 * 
	 * @param rutaMetroDTO
	 *            - donde se fijara la informacion de las listas secundarias
	 */
	void conListasSecundarias(RutaMetroDTO rutaMetroDTO);
	
	/**
	 * Obtiene la informacion de los horarios de la ruta indicada como
	 * argumento y fija a este mismo objeto esta informacion.
	 * 
	 * @param rutaMetroDTO
	 *            - funciona como filtro de busqueda(el id no puede ser null) y
	 *            estos horarios encontradas se fijan a este.
	 */
	void conHorarios(RutaMetroDTO rutaMetroDTO);
	
	/**
	 * Obtiene la informacion de las frecuencias de la ruta indicada como
	 * argumento y fija a este mismo objeto esta informacion.
	 * 
	 * @param rutaMetroDTO
	 *            - funciona como filtro de busqueda(el id no puede ser null) y
	 *            estas frecuencias encontradas se fijan a este.
	 */
	void conFrecuencias(RutaMetroDTO rutaMetroDTO);
	
	/**
	 * Obtiene la informacion de las estaciones de la linea indicada como
	 * argumento y fija a este mismo objeto esta informacion encontrada.
	 * 
	 * @param rutaMetroDTO
	 *            - funciona como filtro de busqueda(el id no puede ser null) y
	 *            estos paraderos encontradas se fijan a este.
	 */
	void conParaderos(RutaMetroDTO rutaMetroDTO);
	
	/**
	 * Obtiene todas las rutas activas
	 * Creado 2/08/2018 04:11:10 p.m
	 * @return conjunto de todas las rutas del metro y GTPC
	 */
	List<RutaMetroDTO> findAllActivas();
	
	/**
	 * Obtiene todas las rutas de una fuente de datos
	 * <P>
	 * Creado 3/10/2018 03:30:10 p.m
	 * @param fuenteDatos - arreglo de fuente de datos a filtrar
	 * @return las rutas de una fuente de datos especifica
	 */
	List<RutaMetroDTO> findByFuenteDatos(Integer... fuenteDatos);

	/**
	 * Encuentra las rutas que coincidan con los codigos y fuente de datos
	 * especificados en los parametros de entrada.
	 * <P>
	 * Creado 16/11/2018 2:57 p.m
	 * 
	 * @param codigos - codigo de las rutas a filtrar
	 * @param fuenteDatos - filtro de busqueda
	 * 
	 * @return un listado de objetos {@link RutaMetroDTO} coincidentes
	 */
	List<RutaMetroDTO> findByCodigoAndFuenteDatos(List<String> codigos, Integer fuenteDatos);
}
