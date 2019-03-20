package co.gov.metropol.area247.centrocontrol.seguridad.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.TareaProgramada;


public interface ISeguridadTareaProgramadaService {
	/**
	 * Metodo para retornar todas las tareas Programadas
	 * @return Lista de tareas Programadas
	 * @throws Exception si algo falla
	 */
	List<TareaProgramada> tareaProgramadaObtenerTodos()throws Exception;
	/**
	 * Metodo para obtener tarea Programada por id
	 * @param id de la tarea Programada a consultar
	 * @return tarea Programada con id especificado
	 * @throws Exception
	 */
	TareaProgramada tareaProgramadaObtenerPorId(Long id)throws Exception;
	/**
	 * Metodo para guardar / crear una tarea Programada
	 * @param nueva tarea Programada a crear
	 * @return tarea Programada a agregar
	 * @throws Exception
	 */
	boolean tareaProgramadaGuardar(TareaProgramada tareaProgramada);
	/**
	 * Metodo para borrar una tarea Programada con id especificado
	 * @param id de la tarea Programada a borrar
	 * @return true si sale bien, false si hubo un error
	 */
	boolean tareaProgramadaBorrar(Long id);
	/**
	 * Metodo para retornar todas las tareas Programadas con un estado determinado
	 * @return Lista de tareas Programadas
	 * @throws Exception si algo falla
	 */		
	List<TareaProgramada> findByActivo(Boolean activo) throws Exception; 

}
