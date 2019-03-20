package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.EstructuraDeArchivoDTO;

public interface IEstructuraDeArchivoService {
	
	/**
	 * Obtener todos elementos activos de estructura de un tipo de carga
	 * @param tipoArchivoId - identificador de un tipo de carga
	 * @return List {@link EstructuraDeArchivoDTO}
	 * */
	List<EstructuraDeArchivoDTO> findAll(Long tipoArchivoId);
	
	/**
	 * Obtener todos elementos activos de estructura de un tipo de carga de acuerdo al estado
	 * @param enabled - estado de activacion
	 * @param tipoArchivoId - identificador de un tipo de carga
	 * @return List {@link EstructuraDeArchivoDTO}
	 * */
	List<EstructuraDeArchivoDTO> findByEnabled(boolean enabled, Long tipoArchivoId);
	
	/**
	 * Obtener el elemento de la estructura de un tipo de carga por el Id.
	 * @param estructuraId - identificador unico.
	 * @return {@link EstructuraDeArchivoDTO}
	 * */
	EstructuraDeArchivoDTO findById (Long estructuraId);
	
	/**
	 * Registrar un nuevo elemento de estructura de un tipo de carga
	 * @param estructuraDeArchivoModel - {@link EstructuraDeArchivoDTO}
	 * */
	void save(EstructuraDeArchivoDTO estructuraDeArchivoModel);
	
	/**
	 * Actualizar un elemento de la estructura de un tipo de carga
	 * @param estructuraDeArchivoModel - {@link EstructuraDeArchivoDTO}
	 * */
	void update(EstructuraDeArchivoDTO estructuraDeArchivoModel);
	
	/**
	 * Borrar un elemento de la estructura de un tipo de carga
	 * @param estructuraId - identificador unico de una estructura de un tipo de carga
	 * */
	void delete(Long estructuraId);
	
	/**
	 * Borrar un listado de elementos de una estructura definida de un tipo de carga
	 * @param estructuraIds - lista de elementos de estructura de un tipo de carga
	 * @param tipoArchivoId - identificador del tipo de archivo al cual esta asociado la estructura
	 * */
	void delete(Long[] estructuraIds, Long tipoArchivoId);
	
	/**
	 * Reorganizar el orden de las diferentes estructuras del tipo de archivo
	 * @param tipoArchivoId - identificador unico del tipo de archivo al cual se actualizan las estructuras.
	 * */
	void updateOrderToStructure(Long tipoArchivoId);
	
	/**
	 * Actualizar la estructura del archivo y el orden del mismo.
	 * @param estructuraDeArchivoModel - {@link EstructuraDeArchivoDTO}
	 * @return boolean
	 * */
	boolean updateEstructuraAndOrden(EstructuraDeArchivoDTO estructuraDeArchivoModel);

}
