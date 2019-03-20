package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import co.gov.metropol.area247.contenedora.model.Marcador;

public interface IContenedoraMarcadorService {
	
	boolean marcadorCrear(Marcador marcador);
	boolean marcadorActualizar(Marcador marcador);
	boolean marcadorEliminar(Long idMarcador);
	Marcador obtenerMarcadorPorId(Long idMarcador);
	Marcador obtenerMarcadorPorNombre(String nombreMarcador);
	List<Marcador> obtenerMarcadorPorCodigo(int codigoMarcador, Long idCapa);
	Marcador obtenerMarcadorPorCategoriaCodigo(Long idCategoria, int codigo);
	Marcador obtenerMarcadorPorSubCategoriaCodigo(Long idSubCategoria, int codigo);
	boolean eliminarMarcadores(List<Marcador> marcadores);
	
	List<Marcador> obtenerPorCapaSinMunicipio(Long idCapa); 
	boolean eliminarMarcadoresPorIdCapa(Long idCapa);
	boolean eliminarMarcadoresPorIdCategoria(Long idCategoria);
}
