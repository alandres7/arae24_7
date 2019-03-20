package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.AutoridadMunicipio;
import co.gov.metropol.area247.centrocontrol.model.dto.AutoridadMunicipioDto;

public interface ICentroControlAutoridadMunicipioService {
	
	List<AutoridadMunicipioDto> autoridadMunicipioPorIdNodo(Long idNodo);
	
	AutoridadMunicipioDto autoridadMunicipioPorIdNodoAndMunicipio(Long idNodo, String Municipio);
	
	boolean autoridadMunicipioGuardar(AutoridadMunicipio autoridadMunicipio);
	
	boolean autoridadMunicipioEliminarByIdNodoArbol(Long idNodoArbol);

}
