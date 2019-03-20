package co.gov.metropol.area247.avistamiento.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.avistamiento.dao.IAvistamientoComentarioRepository;
import co.gov.metropol.area247.avistamiento.model.Avistamiento;
import co.gov.metropol.area247.avistamiento.model.Comentario;
import co.gov.metropol.area247.avistamiento.model.dto.ComentarioDto;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoAvistamientoService;
import co.gov.metropol.area247.avistamiento.service.IAvistamientoComentarioService;
import co.gov.metropol.area247.seguridad.model.Usuario;
import co.gov.metropol.area247.seguridad.service.ISeguridadUsuarioService;

@Service
public class IAvistamientoComentarioServiceImpl implements IAvistamientoComentarioService {

	@Autowired
	IAvistamientoComentarioRepository comentarioDao;

	@Autowired
	IAvistamientoAvistamientoService avistamientoService;

	@Autowired
	ISeguridadUsuarioService usuarioService;

	@Override
	public boolean comentarioCrear(Comentario comentario, Long idAvistamiento) {
		try {
			if (idAvistamiento != null) {
				Avistamiento avistamiento = avistamientoService.avistamientoPorId(idAvistamiento);
				if (avistamiento != null) {
					comentario.setAvistamiento(avistamiento);
				} else {
					return false;
				}
			}
			comentarioDao.save(comentario);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean comentarioActualizar(Comentario comentario) {
		return comentarioCrear(comentario, null);
	}

	@Override
	public ComentarioDto comentarioDtoPorId(Long idComentario) {
		ComentarioDto comentarioDto = comentarioDao.obtenerComentarioPorId(idComentario);				
		comentarioDto.setUsername(obtenerUsernamePorIdUsuario(comentarioDto.getIdUsuario()));
		return comentarioDto;
	}

	@Override
	public Comentario comentarioPorId(Long idComentario) {
		return comentarioDao.findOne(idComentario);
	}

	@Override
	public List<ComentarioDto> comentarioObtenerPorIdAvistamiento(Long idAvistamiento, String nickname) {
		try {
			List<ComentarioDto> comentariosDto = comentarioDao.obtenerComentarioPorIdAvistamiento(idAvistamiento,
					nickname);
			for (ComentarioDto comentarioDto : comentariosDto) {
				comentarioDto.setUsername(obtenerUsernamePorIdUsuario(comentarioDto.getIdUsuario()));
			}
			return comentariosDto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean comentarioEliminar(Long idComentario) {
		try {
			Comentario comentario = comentarioDao.findOne(idComentario);
			if (comentario != null) {
				comentarioDao.delete(comentario);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<ComentarioDto> comentarioObtenerPorIdAvistamiento(Long idAvistamiento) {
		try {
			List<ComentarioDto> comentariosDto = comentarioDao.obtenerComentarioPorIdAvistamiento(idAvistamiento);
			for (ComentarioDto comentarioDto : comentariosDto) {				
				comentarioDto.setUsername(obtenerUsernamePorIdUsuario(comentarioDto.getIdUsuario()));				
			}
			return comentariosDto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
