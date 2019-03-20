package co.gov.metropol.area247.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.seguridad.model.NivelEducativo;

public interface ISeguridadNivelEducativoService {

	List<NivelEducativo> nivelEducativoListarTodos();
	NivelEducativo nivelEducativoObtenerPorId(Long nivelEducativoId);
	NivelEducativo nivelEducativoObtenerPorNombre(String nivelEducativoNombre);
	
}
