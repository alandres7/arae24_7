package co.gov.metropol.area247.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.seguridad.model.FuenteRegistro;

public interface ISeguridadFuenteRegistroService {
	
	List<FuenteRegistro> fuenteRegistroListarTodas();
	FuenteRegistro fuenteRegistroObtenerPorId(Long fuenteRegistroId);
	FuenteRegistro fuenteRegistroObtenerPorNombre(String fuenteRegistroNombre);

}
