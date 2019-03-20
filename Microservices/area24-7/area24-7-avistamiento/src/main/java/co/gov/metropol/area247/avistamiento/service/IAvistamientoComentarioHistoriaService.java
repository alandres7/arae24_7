package co.gov.metropol.area247.avistamiento.service;

import java.util.List;

import co.gov.metropol.area247.avistamiento.model.ComentarioHistoria;
import co.gov.metropol.area247.avistamiento.model.dto.ComentarioHistoriaDto;

public interface IAvistamientoComentarioHistoriaService {
	
	boolean comentarioHistoriaCrear(ComentarioHistoria comentarioHistoria, Long idHistoria);
	boolean comentarioHistoriaActualizar(ComentarioHistoria comentarioHistoria);
	ComentarioHistoriaDto comentarioHistoriaDtoPorId(Long idComentarioHistoria);
	ComentarioHistoria comentarioHistoriaPorId(Long idComentarioHistoria);
	boolean comentarioHistoriaEliminar(Long idComentarioHistoria);
	List<ComentarioHistoriaDto> comentarioHistoriaObtenerPorIdHistoria(Long idHistoria, String nickname);
	List<ComentarioHistoriaDto> comentarioHistoriaObtenerPorIdHistoria(Long idHistoria);

}
