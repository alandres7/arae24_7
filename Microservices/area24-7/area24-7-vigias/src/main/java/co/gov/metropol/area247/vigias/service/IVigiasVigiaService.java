package co.gov.metropol.area247.vigias.service;

import java.time.LocalDate;
import java.util.List;

import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.vigias.model.Vigia;
import co.gov.metropol.area247.vigias.model.dto.VigiaDto;

public interface IVigiasVigiaService {

	boolean vigiaCrear(Vigia vigia); 
	
	boolean vigiaActalizar(Vigia vigia);
	
	boolean vigiaEliminar(Long idVigia);
	
	VigiaDto vigiaDtoConsultarPorIdVigiaOIdMarcador(Long idVigia, Long idMarcador);
	
	Vigia vigiaConsultarPorId(Long idVigia);
	
	List<VigiaDto> vigiaDtoObtenerTodos();
	
	List<VigiaDto> vigiaDtoPorIdUsuario(Long idUsuario);
	
	List<MarkerPoint> obtenerMarcadoresVigiaPorIdUsuarioOEstado(Long idUsuario, String Estado);
	
    List<VigiaDto> vigiaDtoPorEstado(String estado);
    
    int obtenerCantidadVigiasPorParametros(String idCapa,String idCategoria, String estado,
    		LocalDate fechaInicio, LocalDate fechaFin,boolean comenPen);
    
    List<VigiaDto> getVigiaPaginatedPorParametros(Long idCapa, LocalDate desde, LocalDate hasta, 
			Long idCategoria, String whereClause, String orderClause, int pageStart, int pageSize, 
			String estadosList, boolean comenPen);
    
    int getCantidadVigiasPaginatedPorParametros(Long idCapa,Long idCategoria, String estadosList, 
			LocalDate fechaInicio, LocalDate fechaFin,boolean comenPen);
    
    int getCantidadVigiasPaginatedAndFilteredPorParametros(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria, String whereClause, String estadosList,boolean comenPen);
    
    List<MarkerPoint> obtenerMarcadoresVigiaPorLatYLonYRadioYCapa(Double latitud, Double longitud, 
    		int radioAccion, Long idCapa, String nivelCapa);
	
}
