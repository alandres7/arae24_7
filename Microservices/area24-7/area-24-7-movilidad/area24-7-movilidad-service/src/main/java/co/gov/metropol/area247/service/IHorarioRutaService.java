package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.HorarioRutaMetroDTO;

public interface IHorarioRutaService {

	/**
	 * Buscar el horario de la ruta del metro dado el identificador de esta
	 * 
	 * @param idItem
	 *            - filtro de busqueda
	 * @param fuenteDatos
	 *            - filtro de busqueda
	 * 
	 * @return - {@link HorarioRutaMetroDTO}
	 */
	HorarioRutaMetroDTO findByIdItemAndFuenteDatos(Long idItem, Integer fuenteDatos);

	/**
	 * Guardado de el horario de la ruta del metro.
	 * 
	 * @param horarioDTO
	 *            - {@link HorarioRutaMetroDTO}
	 */
	void saveHorarioRuta(HorarioRutaMetroDTO horarioDTO);

	/**
	 * Actualizar la informacion de el horario de la ruta proporcionada por el
	 * servicio del metro
	 * 
	 * @param horarioDTO
	 *            - {@link HorarioRutaMetroDTO}
	 */
	void updateHorarioRuta(HorarioRutaMetroDTO horarioDTO);

	/**
	 * Buscar una horario de la ruta dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico de el horario de la ruta.
	 * @return - {@link HorarioRutaMetroDTO}}
	 */
	HorarioRutaMetroDTO findById(Long id);

	/**
	 * Guardar o actualizar un horario de ruta de metro
	 * 
	 * @param horarioDTO
	 *            - contiene la información a guardar o actualizar
	 */
	void procesarHorario(HorarioRutaMetroDTO horarioDTO);

	/**
	 * Guarda, actualiza o elimina registros de los horarios de una ruta. Todos
	 * los elementos de la lista definida en el argumento de entrada deben
	 * pertenecer a una ruta.
	 * 
	 * @param horariosDTO
	 *            - lista de horarios a procesar, estos horarios deben tener un
	 *            mismo objeto de ruta.
	 */
	void procesarHorarios(List<HorarioRutaMetroDTO> horariosDTO);

	/**
	 * Busca todos los horarios que pertenecen a una ruta.
	 * 
	 * @param idRuta
	 *            - filtro de busqueda
	 * 
	 * @return una lista de objetos de tipo {@link HorarioRutaMetroDTO}
	 */
	List<HorarioRutaMetroDTO> findByIdRuta(Long idRuta);

}
