package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.model.IconoEstado;


public interface IContenedoraIconoEstadoService {
	
	public boolean iconoEstadoRegistrar(String nivelCapa,Long idCapaCategoria,
                                        int idEstado,MultipartFile icono);
	
	boolean iconoEstadoActualizar(IconoEstado iconoEstado,MultipartFile icono);
	
	List<IconoEstado> iconoEstadoPorIdCapa(Long idCapa);
	
	IconoEstado iconoEstadoPorCapaEstado(Long idCapa, int estado);
	
	List<IconoEstado> iconoEstadoPorIdCategoria(Long idCategoria);
	
	IconoEstado iconoEstadoPorCategoriaEstado(Long idCategoria, int estado);
	
	boolean iconoEstadoEliminarByCapa(Long idCapa, int estado);
	
	boolean iconoEstadoEliminarByCategoria(Long idCategoria, int estado);
	
}
