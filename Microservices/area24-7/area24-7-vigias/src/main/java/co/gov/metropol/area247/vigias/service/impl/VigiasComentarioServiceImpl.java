package co.gov.metropol.area247.vigias.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.centrocontrol.model.ArbolDecision;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;
import co.gov.metropol.area247.vigias.dao.IVigiasComentarioRepository;
import co.gov.metropol.area247.vigias.model.ComentarioVigia;
import co.gov.metropol.area247.vigias.model.dto.ComentarioVigiaDto;
import co.gov.metropol.area247.vigias.service.IVigiasComentarioService;
import co.gov.metropol.area247.vigias.service.IVigiasVigiaService;

@Service
public class VigiasComentarioServiceImpl implements IVigiasComentarioService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(VigiasComentarioServiceImpl.class);
	
	@Autowired
	IVigiasComentarioRepository comentarioRepository;

	@Autowired
	IVigiasVigiaService vigiaService;
	
	@Autowired
	ISeguridadUsuarioService usuarioService;

	
	@Override
	public boolean comentarioVigiaCrear(ComentarioVigia comentarioVigia) {
		try {
			comentarioRepository.save(comentarioVigia);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	

	@Override
	public boolean comentarioVigiaActalizar(ComentarioVigia comentarioVigia) {
		try {
			comentarioRepository.save(comentarioVigia);
			return true;
		} catch (Exception e) {
			LOGGER.error("error al actualizar el reporte de vigia con id --{}{}",e);
			return false;
		}
	}

	
	@Override
	public boolean comentarioVigiaEliminar(Long idComentarioVigia) {
		try {
			ComentarioVigia comentarioVigia = comentarioRepository.findOne(idComentarioVigia);
			if(comentarioVigia!=null) {							
				comentarioRepository.delete(idComentarioVigia);
				return true;				
			}else {
				return false;
			}	
		} catch (Exception e) {
			return false;
		}
	}

	
	@Override
	public ComentarioVigiaDto comentarioVigiaDtoConsultarPorId(Long idComentarioVigia) {
		ComentarioVigiaDto comentarioVigiaDto = comentarioRepository.ComentarioVigiaDtoConsultarPorId(idComentarioVigia);		
		if(comentarioVigiaDto!=null) {
			Usuario usuario = usuarioService.usuarioObtenerPorId(comentarioVigiaDto.getIdUsuario());
			comentarioVigiaDto.setUsername(usuario.getUsername());
			return comentarioVigiaDto;
		}else {
			return null;
		}
	}
	
	
	@Override
	public List<ComentarioVigiaDto> comentarioVigiaDtoObtenerPorReporteVigia(Long idReporteVigia){				
		List<ComentarioVigiaDto> listComentarios = comentarioRepository.comentarioVigiaDtoObtenerPorReporteVigia(idReporteVigia);			
		if(!listComentarios.isEmpty()){
		    for (ComentarioVigiaDto comentario : listComentarios) {
		    	Usuario usuario = usuarioService.usuarioObtenerPorId(comentario.getIdUsuario());
		    	comentario.setUsername(usuario.getUsername());			
		    }
	    }
		return listComentarios;
	}
	

}
