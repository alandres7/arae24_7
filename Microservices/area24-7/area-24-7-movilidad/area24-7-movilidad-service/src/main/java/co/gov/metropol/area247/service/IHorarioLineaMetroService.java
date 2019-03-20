package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.HorarioLineaMetroDTO;

public interface IHorarioLineaMetroService {

	/**
	 * Buscar los horarios de la linea que coincida con el identificador de la
	 * linea fijado como parametro.
	 * 
	 * @param idLinea
	 *            - identificador de la linea
	 *
	 * @return - Lista de objetos {@link HorarioLineaMetroDTO}
	 */
	List<HorarioLineaMetroDTO> findByIdLinea(Long idLinea);

	/**
	 * Guardado del horario de la linea del metro.
	 * 
	 * @param horarioLineaMetroDTO
	 *            - {@link HorarioLineaMetroDTO}
	 */
	void saveHorarioLinea(HorarioLineaMetroDTO horarioLineaMetroDTO);

	/**
	 * Actualizar la informacion del horario de la linea proporcionada por el
	 * servicio del metro
	 * 
	 * @param horarioLineaMetroDTO
	 *            - {@link HorarioLineaMetroDTO}
	 */
	void updateHorarioLinea(HorarioLineaMetroDTO horarioLineaMetroDTO);

	/**
	 * Buscar una horario de la linea dado el Id en base de datos
	 * 
	 * @param id
	 *            - identificador unico del horario de la linea.
	 * @return - {@link HorarioLineaMetroDTO}}
	 */
	HorarioLineaMetroDTO findById(Long id);

	/**
	 * Guardar o actualizar todas las horarios de las lineas del metro pasadas
	 * como argumentos
	 * 
	 * @param horariosLineaMetroDTO
	 *            - lista de horarios de lineas del metro
	 */
	void procesarHorarios(List<HorarioLineaMetroDTO> horariosLineaMetroDTO);

	/**
	 * Guarda o actualiza el horario
	 * 
	 * @param horarioLineaMetroDTO
	 *            - objeto a procesar
	 */
	void procesarHorario(HorarioLineaMetroDTO horarioLineaMetroDTO);

	/**
	 * Busca información que coincida con el identificador del horario fijado en
	 * el argumento
	 * 
	 * @param idHorario
	 *            - filtro de busqueda
	 *            
	 * @return {@link HorarioLineaMetroDTO}
	 */
	HorarioLineaMetroDTO findByIdHorario(Long idHorario);

}
