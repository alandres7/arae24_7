package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.CargaArchivoDTO;

public interface ICargaArchivoService {
	
	/**
	 * Leer y procesar archivo del Runt
	 * @param cargaArchivoDTO - Objeto que se recive desde la vista con informacion del archivo a cargar
	 * */
	void procesarArchivoRunt (CargaArchivoDTO cargaArchivoDTO);

}
