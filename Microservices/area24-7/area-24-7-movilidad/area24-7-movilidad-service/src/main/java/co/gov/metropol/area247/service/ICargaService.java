package co.gov.metropol.area247.service;

import java.io.IOException;

import co.gov.metropol.area247.model.CargaArchivoDTO;

public interface ICargaService {

	/**
	 * Validar archivo de carga
	 * @param cargaArchivoDTO - Objeto que se recibe desde la vista con información del archivo a cargar
	 * @throws IOException - IOException 
	 * */		
	void validarArchivo(CargaArchivoDTO cargaArchivoDTO) throws IOException;
	
	/**
	 * Procesar archivo de carga
	 * @param cargaArchivoDTO - Objeto que se recibe desde la vista con información del archivo a cargar
	 * @throws IOException  - IOException  
	 * */	
	void procesarArchivo (CargaArchivoDTO cargaArchivoDTO) throws IOException;
		
}
