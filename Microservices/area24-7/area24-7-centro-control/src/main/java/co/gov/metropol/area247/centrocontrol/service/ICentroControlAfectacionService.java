package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.Afectacion;

public interface ICentroControlAfectacionService {
	/**
	 * Metodo para retornar todas las afectaciones
	 * @return Lista de afectaciones
	 * @throws Exception si algo falla
	 */
	List<Afectacion> afectacionObtenerTodos()throws Exception;
	/**
	 * Metodo para obtener afectacion por id
	 * @param id de la afectacion a consultar
	 * @return afectacion con id especificado
	 * @throws Exception
	 */
	Afectacion afectacionObtenerPorId(Long id)throws Exception;
	/**
	 * Metodo para guardar / crear una afectacion
	 * @param nueva afectacion a crear
	 * @return afectacion a agregar
	 * @throws Exception
	 */
	Afectacion afectacionGuardar(Afectacion afectacion)throws Exception;
	/**
	 * Metodo para borrar una afectacion con id especificado
	 * @param id del afectacion a borrar
	 * @return true si sale bien, false si hubo un error
	 */
	boolean afectacionBorrar(Long id);
}
