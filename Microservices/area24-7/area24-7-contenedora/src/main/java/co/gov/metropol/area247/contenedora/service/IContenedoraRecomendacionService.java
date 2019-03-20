package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.Recomendacion;
import co.gov.metropol.area247.contenedora.model.dto.RecomendacionDto;

public interface IContenedoraRecomendacionService {
	
	boolean recomendacionCrear(Recomendacion recomendacion, Long aplicacionId);
	boolean recomendacionActualizar(Recomendacion recomendacion, Long aplicacionId);
	List<RecomendacionDto> recomendacionObtenerPorAplicacionId(Long aplicacionId);
	List<Recomendacion> recomendacionObtenerTodas();
}
