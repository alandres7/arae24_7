package co.gov.metropol.area247.centrocontrol.carga.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.carga.helper.ErrorCampoArchivoHelper;
import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.dto.CirculacionVehiculoDto;
import co.gov.metropol.area247.centrocontrol.dto.ErrorCampoArchivoDto;

public interface ICirculacionVehiculoService {
	void save(String token, CirculacionVehiculoDto circulacion);
	 
	 void procesarArchivo();

	 void saveFile(CirculacionVehiculoDto circulacionVehiculo);
	 
	 void cargarCirculacionVehiculo();
	 
	 void procesarArchivoCirculacion(int chunkSize, CargaArchivoDto cargaArchivoDTO, int lineas, String separadorCsv, int lineasEncabezado);
	 
	 List<ErrorCampoArchivoDto> validarLineaArchivo(ErrorCampoArchivoHelper eah, String formatoFecha);
}
