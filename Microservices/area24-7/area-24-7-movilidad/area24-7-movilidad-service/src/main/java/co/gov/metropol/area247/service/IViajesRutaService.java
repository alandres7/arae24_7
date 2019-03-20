package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.InfoViajesRutaDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;

public interface IViajesRutaService {

	/**
	 * Obtiene la informacion del viaje que realiza una ruta del metro o GTPC
	 * <P><code>Creado 6/08/2018 03:30:15 p.m</code>
	 * @param idRuta - filtro
	 * @return una lista con la informacion de los viajes de la ruta
	 */
	List<InfoViajesRutaDTO> findByIdRuta(Long idRuta);

	/**
	 * Guarda o actualiza toda la informacion de los viajes de rutas, este 
	 * compara la informacion ya existente y la reemplaza con la informacion
	 * que se encuentra con los argumentos de entrada, puede haber el caso de que 
	 * se elimine informacion existente.
	 * <P><code>Creado 10/08/2018 9:47 a.m</code>
	 * @param infoViajesRutaDTO - lista que contiene toda la informacion a procesar
	 * @param rutaDTO - ruta que se relaciona con la lista de informacion de los viajes
	 */
	void procesarInfoViajesRuta(List<InfoViajesRutaDTO> infoViajesRutaDTO, RutaMetroDTO rutaDTO);

	/**
	 * Elimina la informacion de viajes segun el identificador unico
	 * <P>Creado 10/08/2018 4:24 p.m
	 * @param id - filtro para eliminar
	 */
	void delete(Long id);

	/**
	 * Elimina de BD la informacion de los viajes de las rutas especificados en
	 * la lista.
	 * <P>Creado 10/08/2018 4:24 p.m
	 * @param infosViajesRutas - informacion a eliminar
	 */
	void deleteAll(List<InfoViajesRutaDTO> infosViajesRutas);

	/**
	 * Persiste un elemento
	 * <P>Creado 10/08/2018 4:24 p.m
	 * @param infoViajesRutaDTO
	 *            - elemento a persistir
	 */
	void save(InfoViajesRutaDTO infoViajesRutaDTO);

	/**
	 * Actualiza un elemento
	 * <P>Creado 10/08/2018 4:24 p.m
	 * @param infoViajesRutaDTO - elemento a actualizar
	 */
	void update(InfoViajesRutaDTO infoViajesRutaDTO);

	/**
	 * Persiste o actualiza los elementos de la lista de argumentos. Persiste si
	 * el objeto no tiene id.
	 * <P>Creado 13/08/2018 10:18 a.m
	 * @param infosViajesRuta - lista de objetos a persistir
	 */
	void saveAll(List<InfoViajesRutaDTO> infosViajesRuta);
}
