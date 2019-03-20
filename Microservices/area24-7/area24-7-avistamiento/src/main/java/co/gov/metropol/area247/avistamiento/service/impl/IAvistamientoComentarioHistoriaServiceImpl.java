package co.gov.metropol.area247.avistamiento.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.avistamiento.dao.IAvistamientoComentarioHistoriaRepository;
import co.gov.metropol.area247.avistamiento.model.ComentarioHistoria;
import co.gov.metropol.area247.avistamiento.model.Historia;
import co.gov.metropol.area247.avistamiento.model.dto.ComentarioHistoriaDto;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoComentarioHistoriaService;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoHistoriaService;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;

@Service
public class IAvistamientoComentarioHistoriaServiceImpl implements IAvistamientoComentarioHistoriaService {

	@Autowired
	IAvistamientoComentarioHistoriaRepository comentarioHistoriaDao;
	
	@Autowired
	IAvistamientoHistoriaService historiaService;
	
	@Autowired
	ISeguridadUsuarioService usuarioService;
	
	@Override
	public boolean comentarioHistoriaCrear(ComentarioHistoria comentarioHistoria, Long idHistoria) {
		try {
			if(idHistoria != null) {
			    Historia historia = historiaService.historiaPorId(idHistoria);
			    if(historia != null) {
				    comentarioHistoria.setHistoria(historia);				    
			    } else {
				    return false;
			    }
			}
			comentarioHistoriaDao.save(comentarioHistoria);
		    return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean comentarioHistoriaActualizar(ComentarioHistoria comentarioHistoria) {
		return comentarioHistoriaCrear(comentarioHistoria,null);
	}
	
	@Override
	public ComentarioHistoriaDto comentarioHistoriaDtoPorId(Long idComentarioHistoria) {
		ComentarioHistoriaDto comentarioHistoriaDto = comentarioHistoriaDao.obtenerComentarioHistoriaPorId(idComentarioHistoria);		
		comentarioHistoriaDto.setUsername(obtenerUsernamePorIdUsuario(comentarioHistoriaDto.getIdUsuario()));		
		return comentarioHistoriaDto;
	}
	
	@Override
	public ComentarioHistoria comentarioHistoriaPorId(Long idComentarioHistoria) {
		return comentarioHistoriaDao.findOne(idComentarioHistoria);
	}

	@Override
	public List<ComentarioHistoriaDto> comentarioHistoriaObtenerPorIdHistoria(Long idHistoria, String nickname) {
		try {		
			List<ComentarioHistoriaDto> comentariosHistoriaDto = comentarioHistoriaDao.obtenerComentarioHistoriaPorIdHistoria(idHistoria, nickname);	
			for (ComentarioHistoriaDto comentarioHistoriaDto : comentariosHistoriaDto) { 
				comentarioHistoriaDto.setUsername(obtenerUsernamePorIdUsuario(comentarioHistoriaDto.getIdUsuario()));
			}		
			return comentariosHistoriaDto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<ComentarioHistoriaDto> comentarioHistoriaObtenerPorIdHistoria(Long idHistoria) {
		try {		
			List<ComentarioHistoriaDto> comentariosHistoriaDto = comentarioHistoriaDao.obtenerComentarioHistoriaPorIdHistoria(idHistoria);	
			for (ComentarioHistoriaDto comentarioHistoriaDto : comentariosHistoriaDto) {  
				comentarioHistoriaDto.setUsername(obtenerUsernamePorIdUsuario(comentarioHistoriaDto.getIdUsuario()));				
			}		
			return comentariosHistoriaDto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean comentarioHistoriaEliminar(Long idComentarioHistoria) {
		try {
			ComentarioHistoria comentarioHistoria = comentarioHistoriaDao.findOne(idComentarioHistoria);
			if(comentarioHistoria!=null) {
				comentarioHistoriaDao.delete(comentarioHistoria);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public String obtenerUsernamePorIdUsuario(Long idUsuario) {
		Usuario usuario = usuarioService.usuarioObtenerPorId(idUsuario);
		if(usuario!=null) {
			return usuario.getUsername();
		}else {
			return "";
		}				
	}

}
