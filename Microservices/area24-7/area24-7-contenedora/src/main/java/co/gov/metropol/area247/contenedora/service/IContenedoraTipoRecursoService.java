package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.TipoRecurso;

public interface IContenedoraTipoRecursoService {

	List<TipoRecurso> tipoRecursoListarTodos();
	TipoRecurso tipoRecursoObtenerPorId(Long idTipoRecurso);
	TipoRecurso tipoRecursoObtenerPorNombre(String nombreTipoRecurso);
	boolean tipoRecursoCrear(TipoRecurso tipoRecurso);
	boolean tipoRecursoEliminar(Long idTipoRecurso);
	boolean tipoRecursoActualizar(TipoRecurso tipoRecurso);
	
}
