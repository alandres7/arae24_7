package co.gov.metropol.area247.avistamiento.service;

import java.util.List;

import co.gov.metropol.area247.avistamiento.model.Historia;
import co.gov.metropol.area247.avistamiento.model.dto.HistoriaDto;

public interface IAvistamientoHistoriaService {

	boolean historiaCrear(Historia historia, Long idAvistamiento);

	boolean historiaActualizar(Historia historia);

	Historia historiaPorId(Long idHistoria);

	HistoriaDto historiaDtoPorId(Long idHistoria);

	boolean historiaEliminar(Long idHistoria);

	List<HistoriaDto> obtenerHistoriaPorParametros(Long idAvistamiento, Long idUsuario, Integer estado);

	List<HistoriaDto> historiaObtenerPorIdAvistamiento(Long idAvistamiento, String nickname);

	List<HistoriaDto> getHistoriasPorIdAvistamiento(Long idAvistamiento);

}
