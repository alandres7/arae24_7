package co.gov.metropol.area247.centrocontrol.carga.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.dto.CargaArchivoDto;
import co.gov.metropol.area247.centrocontrol.model.LogCarga;

public interface ILogCargaService {

	List<LogCarga> consultarLogCarga(int maxRows, String usuario);
	
	void saveLog(CargaArchivoDto cargaArchivoDTO, String usuario);

}
