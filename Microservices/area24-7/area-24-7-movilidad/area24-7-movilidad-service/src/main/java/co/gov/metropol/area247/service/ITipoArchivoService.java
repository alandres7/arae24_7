package co.gov.metropol.area247.service;

import java.util.List;

import co.gov.metropol.area247.model.TipoArchivoDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ExtensionesDeArchivo;

public interface ITipoArchivoService {
	
	/**
	 * Obtener todos los tipos de carga de archivo
	 * @return List {@link TipoArchivoDTO}
	 * */
	List<TipoArchivoDTO> findAll();
	
	/**
	 * Obtener todos los tipos de carga de archivo de acuerdo al estado de activacion
	 * @param enabled - estado de activacion
	 * @return List {@link TipoArchivoDTO}
	 * */
	List<TipoArchivoDTO> findByEnabled(boolean enabled);
	
	/**
	 * Obtener todos los tipos de carga de archivo de acuerdo a la extencion que esta maneja
	 * @param extension - {@link ExtensionesDeArchivo}
	 * @return List {@link TipoArchivoDTO}
	 * */
	List<TipoArchivoDTO> findByExtension(ExtensionesDeArchivo extension);
	
	/**
	 * Obtener el tipo de carga de archivo de acuerdo a su identificador unico
	 * @param loadFileTypeId - identificador unico.
	 * @return {@link TipoArchivoDTO}
	 * */
	TipoArchivoDTO findById (Long loadFileTypeId);
	
	/**
	 * Obtener el tipo de carga de archivo de acuerdo a su nombre
	 * @param name - nombre del tipo de carga
	 * @return {@link TipoArchivoDTO}
	 * */
	TipoArchivoDTO findByName (String name);
	
	/**
	 * Registrar un nuevo tipo de carga.
	 * @param loadFileTypeModel - {@link TipoArchivoDTO}
	 * */
	void save(TipoArchivoDTO loadFileTypeModel);
	
	/**
	 * Actualizar un tipo de carga existente
	 * @param loadFileTypeModel - {@link TipoArchivoDTO}
	 * */
	void update(TipoArchivoDTO loadFileTypeModel);
	
	/**
	 * Borrar un tipo de carga de archivo de acuerdo a su identificador unico
	 * @param loadFileTypeId - identificador unico del tipo de carga
	 * */
	void delete(Long loadFileTypeId);
	
	/**
	 * Borrar un listado de tipos de carga de acuerdo a su identificador unico
	 * @param loadFileTypeIds - lista de identificadores a borrar.
	 * */
	void delete(Long[] loadFileTypeIds);

}
