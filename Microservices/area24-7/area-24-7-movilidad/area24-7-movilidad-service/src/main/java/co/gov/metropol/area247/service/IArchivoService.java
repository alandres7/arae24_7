package co.gov.metropol.area247.service;

import java.util.Date;
import java.util.List;

import co.gov.metropol.area247.model.ArchivoDTO;

public interface IArchivoService {
	
	/**
	 * Listar todos los archivos cargados
	 * @return lista de tipo ArchivoDTO
	 */
	List<ArchivoDTO> findAll();
	
	/**
	 * Listar todos los archivos cargados de acuerdo al estado
	 * @param enabled - indica cual es el estado que se desea verificar
	 * @return lista de tipo ArchivoDTO
	 */
	List<ArchivoDTO> findByEnabled(boolean enabled);
	
	/**
	 * Listar todos los archivo cargados para el tipo de archivo seleccionado
	 * @param tipoArchivoId - identificador unico del tipo de archivo.
	 * @return lista de tipo ArchivoDTO
	 * */
	List<ArchivoDTO> findByTipoArchivo (Long tipoArchivoId);
	
	/**
	 * Listar todos los archivos cargados para la fecha seleccionada.
	 * @param fechaCarga - fecha para la consulta.
	 * @return lista de tipo ArchivoDTO
	 * */
	List<ArchivoDTO> findByFechaCarga (Date fechaCarga);
	
	
	/**
	 * Guardar un archivo cargado por la aplicacion
	 * @param archivoDTO - informacion del archivo a guardar
	 * */
	void save(ArchivoDTO archivoDTO);
	
	/**
	 * Actualizar la informacion del estado del archivo cargado
	 * @param archivoDTO - informacion del archivo cargado.
	 * */
	void update(ArchivoDTO archivoDTO);
}
