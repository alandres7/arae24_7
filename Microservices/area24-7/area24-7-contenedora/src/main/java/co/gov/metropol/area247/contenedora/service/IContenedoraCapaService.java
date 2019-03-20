package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.dto.CapaDto;

public interface IContenedoraCapaService {

	boolean capaCrear(Capa capa, Long idAplicacion);

	Capa capaGetByNombre(String nombre);

	Capa capaGetById(Long idCapa);

	boolean capaActualizar(Capa capa);

	/**
	 * Metodo para obtener capas sin listas como marcadores por id
	 * 
	 * @param idCapa
	 * @return Capa sin las listas con el id dado
	 * @throws Exception
	 */
	CapaDto capaDtoObtenerPorId(Long idCapa) throws Exception;

	/**
	 * Metodo para obtener las capas asociadas a una aplicacion sin listas
	 * 
	 * @param idAplicacion
	 * @return
	 * @throws Exception
	 */
	List<CapaDto> capaDtoObtenerPorIdAplicacion(Long idAplicacion) throws Exception;

	boolean capaEliminar(Long idCapa);

	List<String> capaDtoObtenerNombres() throws Exception;

	List<Capa> obtenerCappasPorIdAplicacion(Long idAplicacion) throws Exception;
	
	List<String> capaDtoObtenerUrls() throws Exception;

}
