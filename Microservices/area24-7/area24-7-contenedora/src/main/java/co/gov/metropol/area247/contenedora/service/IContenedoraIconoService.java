package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.gov.metropol.area247.contenedora.model.Icono;

public interface IContenedoraIconoService {
	
	Long iconoCrear(MultipartFile icono, Long idIcono);
	Icono iconoObtenerPorId(Long iconoId);
	Icono iconoObtenerPorNombre(String iconoNombre);
	List<Icono> iconoObtenerTodos();
	Long iconoActualizar(MultipartFile icono, Long idIcono);
	boolean iconoEliminar(Long iconoId);
}
