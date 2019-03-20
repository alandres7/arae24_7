package co.gov.metropol.area247.avistamiento.service;

import java.util.List;

import co.gov.metropol.area247.avistamiento.model.Comentario;
import co.gov.metropol.area247.avistamiento.model.dto.ComentarioDto;

public interface IAvistamientoComentarioService {
	
	boolean comentarioCrear(Comentario comentario, Long idAvistamiento);
	boolean comentarioActualizar(Comentario comentario);
	ComentarioDto comentarioDtoPorId(Long idComentario);
	Comentario comentarioPorId(Long idComentario);
	boolean comentarioEliminar(Long idComentario);
	
	List<ComentarioDto> comentarioObtenerPorIdAvistamiento(Long idAvistamiento, String nickname);
	
	List<ComentarioDto> comentarioObtenerPorIdAvistamiento(Long idAvistamiento);

}
