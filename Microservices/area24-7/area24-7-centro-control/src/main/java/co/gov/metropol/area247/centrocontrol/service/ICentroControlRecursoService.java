package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.RecursoVigia;

public interface ICentroControlRecursoService {
	/**
	 * Metodo para retornar todas los recursos
	 * @return Lista de recursos
	 * @throws Exception si algo falla
	 */
	List<RecursoVigia> recursoObtenerTodos()throws Exception;
	/**
	 * Metodo para obtener recurso por id
	 * @param id del recurso a consultar
	 * @return recurso con id especificado
	 * @throws Exception
	 */
	RecursoVigia recursoObtenerPorId(Long id)throws Exception;
	/**
	 * Metodo para guardar / crear una recurso
	 * @param nuevo recurso a crear
	 * @return recurso a agregar
	 * @throws Exception
	 */
	RecursoVigia recursoGuardar(RecursoVigia nuevoRecurso)throws Exception;
	/**
	 * Metodo para borrar un recurso con id especificado
	 * @param id del recurso a borrar
	 * @return true si sale bien, false si hubo un error
	 */
	boolean recursoBorrar(Long id);
}
