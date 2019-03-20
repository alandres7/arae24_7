package co.gov.metropol.area247.centrocontrol.carga.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.carga.helper.ErrorCampoArchivoHelper;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorCampoArchivoDto;
import co.gov.metropol.area247.centrocontrol.model.Camara;

public interface ICamaraService {
	
	/**
	 * Validar una linea del archivo a cargar
	 * 
	 * @param eah
	 * @param formatoFecha
	 * @return
	 */
	List<ErrorCampoArchivoDto> validarLinea(ErrorCampoArchivoHelper eah, String formatoFecha);
	
	/**
	 * Cargar archivo de camaras de la secretaria de transito
	 * 
	 * @param chunkSize
	 * @param cargaArchivoDTO
	 * @param lineas
	 * @param separadorCsv
	 * @param lineasEncabezado
	 * @param formatoFecha
	 */
	void procesarArchivo (int chunkSize, CargaArchivoDto cargaArchivoDTO, int lineas, String separadorCsv, int lineasEncabezado, String formatoFecha);

	/**
	 * Consultar camaras en la base de datos
	 * 
	 * @return
	 */
	List<Camara> findAll();

	/**
	 * Eliminar version anterior de registros subidos por segunda vez en una carga (efecto de sobre escribirlos)
	 * 
	 */
	void eliminarDuplicados();
	
	/**
	 * Contar cuantos registros fueron sobre escritos en una carga para informarlo al usuario que hizo la carga
	 * 
	 * @return
	 */
	int contarDuplicados();
}
