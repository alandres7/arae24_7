package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.TipoPregunta;

public interface ICentroControlTipoPreguntaService {
	List<TipoPregunta> tipoPreguntaObtenerTodos()throws Exception;
	TipoPregunta tipoPreguntaObtenerPorId(Long idTipoPregunta)throws Exception;
	TipoPregunta tipoPreguntaGuardar(TipoPregunta tipoPregunta)throws Exception;
	boolean tipoPreguntaEliminar(Long idTipoPregunta)throws Exception;
}
