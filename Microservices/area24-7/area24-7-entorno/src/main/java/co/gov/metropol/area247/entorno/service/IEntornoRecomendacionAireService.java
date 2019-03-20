package co.gov.metropol.area247.entorno.service;

import java.util.List;

import co.gov.metropol.area247.entorno.model.RecomendacionAire;


public interface IEntornoRecomendacionAireService {

	boolean recomendacionCrear(RecomendacionAire recomendacion);

	boolean recomendacionActualizar(RecomendacionAire recomendacion);
	
	public List<RecomendacionAire> recomendacionObtenerTodas();

	RecomendacionAire recomendacionPorCodigo(String codigo);
	
	RecomendacionAire recomendacionPorId(Long idRecomendacion);
	
	public List<RecomendacionAire> recomendacionesPorIdEstacion(Long idEstacion);

	boolean recomendacionEliminar(String codigo);

}
