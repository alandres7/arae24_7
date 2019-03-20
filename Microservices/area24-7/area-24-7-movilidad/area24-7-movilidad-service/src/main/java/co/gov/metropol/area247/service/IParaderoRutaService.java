package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.util.web.Coordenada;

public interface IParaderoRutaService {

	/**
	 * Buscar el paradero de la ruta del metro dado el identificador de esta
	 * 
	 * @param codigo
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda
	 * 
	 * @return - {@link ParaderoRutaMetroDTO}
	 */
	ParaderoRutaMetroDTO findByCodigoAndFuenteDatos(String codigo, Integer fuenteDatos);

	/**
	 * Guardado de el paradero de la ruta del metro.
	 * 
	 * @param paraderoDTO
	 *            - {@link ParaderoRutaMetroDTO}
	 */
	void saveParaderoRuta(ParaderoRutaMetroDTO paraderoDTO);

	/**
	 * Actualizar la informacion de el paradero de la ruta proporcionada por el
	 * servicio del metro
	 * 
	 * @param paraderoDTO
	 *            - {@link ParaderoRutaMetroDTO}
	 */
	void updateParaderoRuta(ParaderoRutaMetroDTO paraderoDTO);

	/**
	 * Buscar una paradero de la ruta dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico de el paradero de la ruta.
	 * @return - {@link ParaderoRutaMetroDTO}}
	 */
	ParaderoRutaMetroDTO findById(Long id);

	/**
	 * Guardar o actualizar un paradero de ruta de metro
	 * 
	 * @param paraderoDTO
	 *            - contiene la información a guardar o actualizar
	 */
	void procesarParadero(ParaderoRutaMetroDTO paraderoDTO);
	
	/**
	 * Obtiene los paraderos de las rutas definidas mas cercanas al punto de
	 * coordenada dado.
	 * 
	 * @param coordenada
	 *            - contiene la latitud, longitud y radio a abarcar
	 * @param modosRecorrido
	 *            - contiene los modos de recorrido de las rutas para filtrar los
	 *            paraderos
	 * @return una lista de objetos tipo {@link EstacionLineaMetroDTO}
	 */
	List<ParaderoRutaMetroDTO> obtenerParaderosCercanos(Coordenada coordenada, List<Long> modosRecorrido);
	
	/**
	 * Obtiene todos los paraderos que coincidan con el id de la ruta que se
	 * especifica como argumento
	 * 
	 * @param idRuta
	 *            - filtro de busqueda
	 * 
	 * @param m2o
	 *            - (ManyToOne) <code>true</code> para obtener la informacion de
	 *            sus llaves foraneas
	 * 
	 * @return lista de objetos tipo {@link ParaderoRutaMetroDTO}
	 */
	List<ParaderoRutaMetroDTO> findByIdRuta(Long idRuta, boolean m2o);
	
	/**
	 * Obtiene todas los paraderos activas de las rutas del metro y gtpc Creado
	 * 06/08/2018 10:26 p.m
	 * 
	 * @return todas los paraderos activos de las rutas
	 */
	List<ParaderoRutaMetroDTO> findAllActivas();
	
	/**
	 * Obtiene todos los paraderos que coincidan con las fuentes de datos
	 * fijadas como parametros
	 * <P>
	 * Creado 3/10/2018 3:40 p.m
	 * 
	 * @param fuenteDatos - arreglo de fuentes de datos a filtrar
	 * @return paraderos tipo {@link ParaderoRutaMetroDTO}
	 */
	List<ParaderoRutaMetroDTO> findByFuenteDatos(Integer... fuenteDatos);

}
