package co.gov.metropol.area247.centrocontrol.carga.service;

import java.io.IOException;

import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.ValidarArchivoDto;

public interface ICargaService {

	/**
	 * Validar archivo de carga
	 * 
	 * @param cargaArchivoDTO - Objeto que se recibe desde la vista con información del archivo a cargar
	 * @throws IOException excepcion para el metodo
	 * */		
	ValidarArchivoDto validarArchivo(CargaArchivoDto cargaArchivoDTO, Long tipoArchivoId, String separadorCsv, int lineasEncabezado, String formatoFecha) throws IOException;
	
}
