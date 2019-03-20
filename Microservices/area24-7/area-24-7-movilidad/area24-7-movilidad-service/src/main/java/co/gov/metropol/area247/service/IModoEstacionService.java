package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.ModoEstacionDTO;

@Deprecated
public interface IModoEstacionService {

	/**
	 * Realiza busqueda por el campo id
	 * 
	 * @param id
	 *            - filtro de busqueda
	 *            
	 * @return objeto {@link ModoEstacionDTO} que coincida con el filtro de
	 *         busqueda
	 */
	ModoEstacionDTO findById(Long id);
	
	/**
	 * Realiza busqueda por el campo nombre
	 * 
	 * @param nombre
	 *            - identificador que obtiene desde el servicio web
	 * 
	 * @return objeto ModoEstacionDTO encontrado, o null si no lo encuentra
	 */
	ModoEstacionDTO findByNombre(String nombre);

	/**
	 * Guardado del tipo de linea.
	 * 
	 * @param modoEstacionDTO
	 *            - {@link ModoEstacionDTO}
	 */
	void saveModoEstacion(ModoEstacionDTO modoEstacionDTO);

	/**
	 * Actualizar la informacion del tipo de la linea
	 * 
	 * @param modoEstacionDTO
	 *            - {@link ModoEstacionDTO}
	 */
	void updateModoEstacion(ModoEstacionDTO modoEstacionDTO);

	/**
	 * Guardar o actualizar el modo de estacion definido como parametro
	 * 
	 * @param modoEstacionDTO
	 *            - objeto a procesar, ya sea para guardarlo como nuevo o para
	 *            actualizarlo
	 */
	void procesarModoEstacion(ModoEstacionDTO modoEstacionDTO);

}
