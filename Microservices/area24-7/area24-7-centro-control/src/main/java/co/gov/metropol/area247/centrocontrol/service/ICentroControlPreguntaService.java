package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Pregunta;
import co.gov.metropol.area247.centrocontrol.model.TipoPregunta;

public interface ICentroControlPreguntaService {
	
	List<Pregunta> preguntaObtenerPorIdFormulario(Long idFormulario)throws Exception;
	List<Pregunta> preguntaObtenerTodas()throws Exception;
	List<Pregunta> preguntaObtenerPorTipo(TipoPregunta tipoPregunta)throws Exception;
	Pregunta preguntaObtenerPorId(Long idPregunta)throws Exception;
	Pregunta preguntaGuardar(Pregunta pregunta)throws Exception;
	boolean preguntaGuardar(List<Pregunta> preguntas)throws Exception;
	boolean preguntaEliminar(Long idPregunta)throws Exception;
}
