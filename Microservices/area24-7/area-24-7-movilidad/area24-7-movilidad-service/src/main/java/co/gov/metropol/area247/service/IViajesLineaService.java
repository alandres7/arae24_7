package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.InfoViajesLineaDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;

public interface IViajesLineaService {
	
	/**
	 * Obtiene la informacion del viaje que realiza una linea del metro
	 * <P><code>Creado 03:30:15 p.m</code>
	 * @param idLinea - filtro
	 * @return viaje de la linea
	 */
	List<InfoViajesLineaDTO> findByIdLinea(Long idLinea);

	/**
	 * Guarda o actualiza toda la informacion de los viajes de lineas, este 
	 * compara la informacion ya existente y la reemplaza con la informacion
	 * que se encuentra con los argumentos de entrada, puede haber el caso de que 
	 * se elimine informacion existente.
	 * <P><code>Creado 10/08/2018 9:47 a.m</code>
	 * @param infoViajesLineaDTO - lista que contiene toda la informacion a procesar
	 * @param LineaDTO - Linea que se relaciona con la lista de informacion de los viajes
	 */
	void procesarInfoViajesLinea(List<InfoViajesLineaDTO> infoViajesLineaDTO, LineaMetroDTO LineaDTO);

	/**
	 * Elimina la informacion de viajes segun el identificador unico
	 * <P>Creado 14/08/2018 4:24 p.m
	 * @param id - filtro para eliminar
	 */
	void delete(Long id);

	/**
	 * Elimina de BD la informacion de los viajes de las Lineas especificados en
	 * la lista.
	 * <P>Creado 14/08/2018 4:24 p.m
	 * @param infosViajesLineas - informacion a eliminar
	 */
	void deleteAll(List<InfoViajesLineaDTO> infosViajesLineas);

	/**
	 * Persiste un elemento
	 * <P>Creado 14/08/2018 4:24 p.m
	 * @param infoViajesLineaDTO
	 *            - elemento a persistir
	 */
	void save(InfoViajesLineaDTO infoViajesLineaDTO);

	/**
	 * Actualiza un elemento
	 * <P>Creado 14/08/2018 4:24 p.m
	 * @param infoViajesLineaDTO - elemento a actualizar
	 */
	void update(InfoViajesLineaDTO infoViajesLineaDTO);

	/**
	 * Persiste o actualiza los elementos de la lista de argumentos. Persiste si
	 * el objeto no tiene id.
	 * <P>Creado 14/08/2018 10:18 a.m
	 * @param infosViajesLinea - lista de objetos a persistir
	 */
	void saveAll(List<InfoViajesLineaDTO> infosViajesLinea);
}
