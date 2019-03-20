package co.gov.metropol.area247.centrocontrol.service;

import java.util.List;

import co.gov.metropol.area247.centrocontrol.model.AutoridadCompetente;

public interface ICentroControlAutoridadCompetenteService {
	/**
	 * Metodo para retornar todas las autoridades competentes
	 * @return Lista de autoridades competentes
	 * @throws Exception si algo falla
	 */
	List<AutoridadCompetente> autoridadCompetenteObtenerTodos()throws Exception;
	/**
	 * Metodo para obtener autoridad por id
	 * @param id de la autoridad a consultar
	 * @return Autoridad con id especificado
	 * @throws Exception
	 */
	AutoridadCompetente autoridadCompetenteObtenerPorId(Long id)throws Exception;
	/**
	 * Metodo para guardar / crear una Autoridad Competente
	 * @param nuevaAutoridad AutoridadCompetente a crear
	 * @return AutoridadCompetente a agregar
	 * @throws Exception
	 */
	AutoridadCompetente autoridadCompetenteGuardar(AutoridadCompetente nuevaAutoridad)throws Exception;
	/**
	 * Metodo para borrar una autoridad con id especificado
	 * @param id de la autoridad a borrar
	 * @return true si sale bien, false si hubo un error
	 */
	boolean autoridadCompetenteBorrar(Long id);
}
