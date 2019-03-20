package co.gov.metropol.area247.vigias.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.vigias.dao.IVigiasVigiaJDBCRepository;
import co.gov.metropol.area247.vigias.dao.IVigiasVigiaRepository;
import co.gov.metropol.area247.vigias.model.Vigia;
import co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto;
import co.gov.metropol.area247.vigias.model.dto.VigiaDto;
import co.gov.metropol.area247.vigias.service.IVigiasComentarioService;
import co.gov.metropol.area247.vigias.service.IVigiasVigiaService;

@Service
public class VigiasVigiaServiceImpl implements IVigiasVigiaService {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VigiasVigiaServiceImpl.class);
	
	@Value("${iconos.server.url}")
	String urlIconos;
	
	@Value("${media.server.url}")
	String urlMedias;
	
	@Autowired
	private IVigiasVigiaRepository vigiaDao;
	
	@Autowired
	private IVigiasVigiaJDBCRepository vigiaJDBCDao;
	
	@Autowired
	private IContenedoraMarcadorService marcadorSvc;
	
	@Autowired
	private IVigiasComentarioService vigiaComentarioService;
	
	
	
	@Override
	public boolean vigiaCrear(Vigia vigia) {
		try {
			vigiaDao.save(vigia);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	

	@Override
	public boolean vigiaActalizar(Vigia vigia) {
		try {
			vigiaDao.save(vigia);
			return true;
		} catch (Exception e) {
			LOGGER.error("error al actualizar el reporte de vigia con id --{}{}",e);
			return false;
		}
	}

	
	@Override
	public boolean vigiaEliminar(Long idVigia) {
		try {
			Vigia vigia = vigiaDao.findOne(idVigia);
			if(vigia!=null) {
				Marcador marcador = vigia.getMarcador();
				if(marcador!=null) {
					marcadorSvc.marcadorEliminar(marcador.getId());
				}			
				
				List<ComentarioVigiaDto> listComentarios 
				    = vigiaComentarioService.comentarioVigiaDtoObtenerPorReporteVigia(idVigia);			
				for (ComentarioVigiaDto comentario : listComentarios) {
					vigiaComentarioService.comentarioVigiaEliminar(comentario.getId());
				}					
				vigiaDao.delete(idVigia);
				return true;				
			}else {
				return false;
			}	
		} catch (Exception e) {
			return false;
		}
	}

	
	@Override
	public VigiaDto vigiaDtoConsultarPorIdVigiaOIdMarcador(Long idVigia, Long idMarcador) {
		VigiaDto vigiaDto = vigiaJDBCDao.vigiaDtoConsultarPorIdVigiaOIdMarcador(idVigia,idMarcador);		
		if(vigiaDto!=null) {
			vigiaDto.setRutaIcono(urlIconos + vigiaDto.getIdIcono());
			vigiaDto.setRutaMultimedia(urlMedias + vigiaDto.getIdMultimedia());
			
			if(vigiaDto.getIdIconoVentana() != null) {
				vigiaDto.setRutaIconoVentana(urlIconos + vigiaDto.getIdIconoVentana());
			}
			return vigiaDto;
		}else {
			return null;
		}
	}
	
	
	@Override
	public Vigia vigiaConsultarPorId(Long idVigia) {
		Vigia vigia = vigiaDao.findOne(idVigia);
		if(vigia!=null) {			
			return vigia;
		}else {
			return null;
		}
	}
	
	
	@Override
	public List<VigiaDto> vigiaDtoObtenerTodos(){				
		List<VigiaDto> listVigiaDto = vigiaJDBCDao.vigiaDtoObtenerTodos();			
		for (VigiaDto vigiaDto : listVigiaDto) {
			vigiaDto.setRutaIcono(urlIconos + vigiaDto.getIdIcono());
			vigiaDto.setRutaMultimedia(urlMedias + vigiaDto.getIdMultimedia());	
			
			if(vigiaDto.getIdIconoVentana() != null) {
				vigiaDto.setRutaIconoVentana(urlIconos + vigiaDto.getIdIconoVentana());
			}
		}
		return listVigiaDto;
	}
	
		
	@Override
	public List<VigiaDto> vigiaDtoPorIdUsuario(Long idUsuario){				
		List<VigiaDto> listVigiaDto = vigiaJDBCDao.vigiaDtoPorIdUsuario(idUsuario);
		for (VigiaDto vigiaDto : listVigiaDto) {
			vigiaDto.setRutaIcono(urlIconos + vigiaDto.getIdIcono());
			vigiaDto.setRutaMultimedia(urlMedias + vigiaDto.getIdMultimedia());	
			
			if(vigiaDto.getIdIconoVentana() != null) {
				vigiaDto.setRutaIconoVentana(urlIconos + vigiaDto.getIdIconoVentana());
			}
		}
		return listVigiaDto;		
	}
	
	
	@Override
	public List<MarkerPoint> obtenerMarcadoresVigiaPorIdUsuarioOEstado(Long idUsuario, String estado){				
		List<MarkerPoint> listMarkerPoint = vigiaJDBCDao.obtenerMarcadoresVigiaPorIdUsuarioOEstado(idUsuario,estado);
		return listMarkerPoint;		
	}
	
	
	@Override
	public List<VigiaDto> vigiaDtoPorEstado(String estado){				
		List<VigiaDto> listVigiaDto = vigiaJDBCDao.vigiaDtoPorEstado(estado);	
		for (VigiaDto vigiaDto : listVigiaDto) {
			vigiaDto.setRutaIcono(urlIconos + vigiaDto.getIdIcono());
			vigiaDto.setRutaMultimedia(urlMedias + vigiaDto.getIdMultimedia());
			
			if(vigiaDto.getIdIconoVentana() != null) {
				vigiaDto.setRutaIconoVentana(urlIconos + vigiaDto.getIdIconoVentana());
			}
		}
		return listVigiaDto;		
	}
	
	
	@Override
	public int obtenerCantidadVigiasPorParametros(String idCapa,String idCategoria, String estado, 
			LocalDate fechaInicio, LocalDate fechaFin,boolean comenPen){	
		return vigiaJDBCDao.obtenerCantidadVigiasPorParametros(idCapa,idCategoria,estado,fechaInicio,fechaFin,comenPen);		
	}
	
	
	@Override
	public List<VigiaDto> getVigiaPaginatedPorParametros(Long idCapa, LocalDate desde, LocalDate hasta, 
			Long idCategoria, String whereClause, String orderClause, int pageStart, int pageSize, 
			String estadosList, boolean comenPen){		
		
		return vigiaJDBCDao.getVigiaPaginatedPorParametros(idCapa,desde,hasta,idCategoria,whereClause,
				orderClause,pageStart,pageSize,estadosList,comenPen);			
	}
	
	
	@Override
	public int getCantidadVigiasPaginatedPorParametros(Long idCapa,Long idCategoria, String estadosList, 
			LocalDate fechaInicio, LocalDate fechaFin,boolean comenPen){
		
		return vigiaJDBCDao.getCantidadVigiasPaginatedPorParametros(idCapa,idCategoria,estadosList, 
				fechaInicio,fechaFin,comenPen);		
	}
	
	@Override
	public int getCantidadVigiasPaginatedAndFilteredPorParametros(Long idCapa, LocalDate desde, LocalDate hasta,
			Long idCategoria, String whereClause, String estadosList,boolean comenPen) {
		
		return vigiaJDBCDao.getCantidadVigiasPaginatedAndFilteredPorParametros(idCapa,desde,hasta,idCategoria,whereClause, 
				estadosList, comenPen);		
	}
	
	@Override
	public List<MarkerPoint> obtenerMarcadoresVigiaPorLatYLonYRadioYCapa(Double latitud, Double longitud, 
    		int radioAccion, Long idCapa, String nivelCapa){
		
		return vigiaJDBCDao.obtenerMarcadoresVigiaPorLatYLonYRadioYCapa(latitud,longitud,radioAccion,idCapa,nivelCapa);		
	}
	

}
